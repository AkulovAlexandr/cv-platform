package by.akulov.java.cvp.web;

import by.akulov.java.cvp.exception.ServerErrorException;
import by.akulov.java.cvp.model.Photo;
import by.akulov.java.cvp.model.resume.Resume;
import by.akulov.java.cvp.repository.PhotoRepository;
import by.akulov.java.cvp.service.ResumeService;
import by.akulov.java.cvp.util.FileSaver;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;


@Controller
@RequestMapping("/upload")
public class PhotoController {


    private final PhotoRepository photoRepository;
    private final ResumeService resumeService;
    public final String PROFILE_PHOTO_PATH;

    @Autowired
    public PhotoController(@Value("${upload.profile-path}") String upload, PhotoRepository photoRepository, ResumeService resumeService) {
        PROFILE_PHOTO_PATH = upload;
        this.photoRepository = photoRepository;
        this.resumeService = resumeService;
    }


    @GetMapping("/profile/{fileName}")
    @ResponseBody
    public HttpEntity<byte[]> getProfilePhoto(@PathVariable String fileName) throws IOException {
        File file = new File("");
        String fullPath = file.getCanonicalPath() + PROFILE_PHOTO_PATH + fileName;
        byte[] image = FileUtils.readFileToByteArray(new File(fullPath));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(image.length);
        return new HttpEntity<>(image, headers);
    }

    @PostMapping("/profile/")
    public String handleFileUpload(@RequestParam(name = "photo") MultipartFile multipartFile,
                                   @ModelAttribute Photo photo,
                                   @RequestParam String resumeId) {
        try {
            String fileName = FileSaver.saveFileTo(multipartFile, PROFILE_PHOTO_PATH);
            if (photo.getId() != null) {
                Optional<Photo> oldPhoto = photoRepository.findById(photo.getId());
                if (oldPhoto.isPresent()) {
                    oldPhoto.get().setName(fileName);
                    photoRepository.save(oldPhoto.get());
                }
            } else {
                photo.setName(fileName);
                photoRepository.save(photo);
                Resume resume = resumeService.findById(Long.parseLong(resumeId));
                resume.setPhoto(photo);
                resumeService.save(resume);
                return "redirect:/cv/list/";
            }
        } catch (RuntimeException | IOException ex) {
            throw new ServerErrorException();
        }
        return String.format("redirect:/cv/edit/%s/", resumeId);
    }

}
