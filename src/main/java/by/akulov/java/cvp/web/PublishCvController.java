package by.akulov.java.cvp.web;

import by.akulov.java.cvp.exception.PageNotFoundException;
import by.akulov.java.cvp.model.PlatformUser;
import by.akulov.java.cvp.model.resume.Resume;
import by.akulov.java.cvp.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PublishCvController {

    private final ResumeService resumeService;

    @Autowired
    public PublishCvController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping("/public/{resumeId}/")
    public String getResumePage(@PathVariable Long resumeId, Model model) {
        Resume resume = resumeService.findById(resumeId);
        if (resume != null && resume.isPublished()) {
            PlatformUser user = resume.getPlatformUser();
            model.addAttribute("resumeOwner", user);
            model.addAttribute("userCredentials", user.getName() + " " + user.getSurname());
            model.addAttribute("resume", resume);
            model.addAttribute("isPublished", true);
            return "cv";
        }
        throw new PageNotFoundException();
    }
}
