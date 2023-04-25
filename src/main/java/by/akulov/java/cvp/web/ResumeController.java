package by.akulov.java.cvp.web;

import by.akulov.java.cvp.model.PlatformUser;
import by.akulov.java.cvp.model.resume.Resume;
import by.akulov.java.cvp.service.ResumeService;
import by.akulov.java.cvp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private UserService userService;

    @GetMapping("/cv/{id}/")
    public String getResumePage(@PathVariable Long id, HttpSession session, Model model) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Resume resume = resumeService.findById(id);
        if (resume != null) {
            PlatformUser resumePlatformUser = resume.getPlatformUser();
            if (resumePlatformUser.getLogin().equals(login)) {
                model.addAttribute("resumeOwner", resumePlatformUser);
                model.addAttribute("resume", resume);
                return "cv";
            } else {
                model.addAttribute("message", "Ошибка открытия резюме. Доступ запрещен");
                return "forward:/cv/list/";
            }
        }
        model.addAttribute("message", "Ошибка открытия резюме");
        return "forward:/cv/list/";
    }

    @GetMapping("/cv/delete/{id}/")
    public String deleteResume(@PathVariable Long id, Model model) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        PlatformUser user = userService.findUserByLogin(login);
        ArrayList<Resume> resumes = resumeService.findAllByUserId(user.getId());
        for (Resume resume : resumes) {
            Long resumeID = resume.getId();
            if ((id.equals(resumeID))) {
                resumeService.deleteResumeById(id);
                //как вместе с редиректом передать message?
                model.addAttribute("message", "Резюме удалено!");
                return "redirect:/cv/list/";
            }
        }
        model.addAttribute("message", "Нет доступа к запрашиваемому резюме!");
        return "forward:/cv/list/";
    }

    @GetMapping("/cv/list/")
    public String getListOfResumes(Model model) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        PlatformUser platformUser = userService.findUserByLogin(login);
        ArrayList<Resume> resumes = new ArrayList<>(platformUser.getResume());
        if (resumes.size() > 0) {
            model.addAttribute("resumes", resumes);
            return "cv-list";
        }
        return "redirect:/cv/create/";
    }

    @GetMapping("/cv/create/")
    public String getCreatePage(@ModelAttribute Resume resume, Model model) {
        model.addAttribute("resume", new Resume());
        return "cv-edit";
    }

    @PostMapping("/cv/create/")
    public String saveResume(@ModelAttribute Resume resume, Model model) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        resume.setPlatformUser(userService.findUserByLogin(login));
        if (resumeService.save(resume) != null) {
            model.addAttribute("message", "Резюме сохранено!");
        } else {
            model.addAttribute("message", "Не сохранилось");
        }
        return "cv-edit";
    }


}
