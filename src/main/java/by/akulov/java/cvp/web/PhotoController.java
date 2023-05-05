package by.akulov.java.cvp.web;

import by.akulov.java.cvp.model.Photo;
import by.akulov.java.cvp.model.resume.Resume;
import by.akulov.java.cvp.repository.PhotoRepository;
import by.akulov.java.cvp.service.ResumeService;
import jakarta.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Controller
@RequestMapping("/upload")
public class PhotoController {


    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private ResumeService resumeService;

    public final String PROFILE_PHOTO_PATH;
    public final String APP_CONTEXT_PATH;

    public PhotoController(@Value("${upload.profile-path}") String upload, ServletContext servletContext) {
        PROFILE_PHOTO_PATH = upload;
        APP_CONTEXT_PATH = servletContext.getContextPath();
    }


    @GetMapping("/profile/{fileName}")
    @ResponseBody
    public HttpEntity<byte[]> getProfilePhoto(@PathVariable String fileName) throws IOException {
        File file = new File(APP_CONTEXT_PATH);
        String fullPath = file.getCanonicalPath() + PROFILE_PHOTO_PATH + fileName;
        byte[] image = FileUtils.readFileToByteArray(new File(fullPath));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(image.length);
        return new HttpEntity<>(image, headers);
    }

    @PostMapping("/profile/")
    public String handleFileUpload(@RequestParam(name = "photo") MultipartFile multipartFile,
                                   @ModelAttribute Photo photo,
                                   @RequestParam String resumeId) throws IOException {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        if (multipartFile != null) {
            InputStream inputStream = multipartFile.getInputStream();
            byte[] buf = new byte[51200];
            File file = new File(APP_CONTEXT_PATH);
            Date currentDate = new Date();
            String fileName = currentDate + "_" + userLogin + "_" + multipartFile.getOriginalFilename();
            String fullPath = file.getCanonicalPath() + PROFILE_PHOTO_PATH + fileName;
            FileOutputStream fileOutputStream = new FileOutputStream(fullPath);
            int numRead = 0;
            while ((numRead = inputStream.read(buf)) >= 0) {
                fileOutputStream.write(buf, 0, numRead);
            }
            inputStream.close();
            fileOutputStream.close();

            if (photo.getId() != null) {
                Photo oldPhoto = photoRepository.findById(photo.getId()).get();
                oldPhoto.setName(fileName);
                photoRepository.save(oldPhoto);
            } else {
                photo.setName(fileName);
                photoRepository.save(photo);
                Resume resume = resumeService.findById(Long.parseLong(resumeId));
                resume.setPhoto(photo);
                resumeService.save(resume);
            }


        }
        return "redirect:/cv/edit/" + resumeId + "/";
    }
}
