package by.akulov.java.cvp.web;

import by.akulov.java.cvp.model.Resume;
import by.akulov.java.cvp.model.User;
import by.akulov.java.cvp.service.ResumeService;
import by.akulov.java.cvp.service.SecurityService;
import by.akulov.java.cvp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Controller
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private UserService userService;

    @GetMapping("/cv/{id}/")
    public String getResumePage(@PathVariable Long id, HttpSession session, Model model) {
        User authorizedUser = (User) session.getAttribute("user");
        if (authorizedUser != null) {
            Resume resume = resumeService.findById(id);
            if (resume != null) {
                User resumeUser = resume.getUser();
                if (resumeUser.equals(authorizedUser)) {
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
        return "redirect:/login";
    }

    @GetMapping("/cv/delete/{id}/")
    public String deleteResume(@PathVariable Long id, HttpSession session, Model model) {
        User authorizedUser = (User) session.getAttribute("user");
        if (authorizedUser != null) {
            ArrayList<Resume> resumes = resumeService.findAllByUserId(authorizedUser.getId());
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
        return "redirect:/login";
    }

    @GetMapping("/cv/list/")
    public String getListOfResumes(HttpSession session, Model model) {
        User authorizedUser = (User) session.getAttribute("user");
        if (authorizedUser != null) {
            User user = userService.getUserById(authorizedUser.getId());
            LinkedList<Resume> resumes = new LinkedList<>(user.getResume());
            if (resumes.size() > 0) {
                model.addAttribute("resumes", resumes);
                return "cv-list";
            }
            return "redirect:/cv/create/";
        }
        return "redirect:/login";
    }

    @GetMapping("/cv/create/")
    public String getCreatePage(@ModelAttribute Resume resume, Model model, HttpSession session) {
        User authorizedUser = (User) session.getAttribute("user");
        if (authorizedUser != null) {
            model.addAttribute("resume", new Resume());
            return "cv-edit";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/cv/create/")
    public String saveResume(@ModelAttribute Resume resume, Model model, HttpSession session) {
        resume.setUser((User) session.getAttribute("user"));
        if (resumeService.save(resume) != null) {
            model.addAttribute("message", "Резюме сохранено!");
        } else {
            model.addAttribute("message", "Не сохранилось");
        }
        return "cv-edit";
    }


}
