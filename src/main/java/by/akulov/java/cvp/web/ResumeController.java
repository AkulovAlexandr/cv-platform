package by.akulov.java.cvp.web;

import by.akulov.java.cvp.model.PlatformUser;
import by.akulov.java.cvp.model.resume.Resume;
import by.akulov.java.cvp.model.resume.Skill;
import by.akulov.java.cvp.model.resume.contact.Contact;
import by.akulov.java.cvp.model.resume.experience.Experience;
import by.akulov.java.cvp.model.resume.experience.ExperienceType;
import by.akulov.java.cvp.service.ResumeService;
import by.akulov.java.cvp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private UserService userService;

    @GetMapping("/cv/{id}/")
    public String getResumePage(@PathVariable Long id, Model model) {
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
                model.addAttribute("message", "Резюме удалено!");
                return "redirect:/cv/list/";
            }
        }
        model.addAttribute("message", "Нет доступа к запрашиваемому резюме!");
        return "redirect:/cv/list/";
    }

    @GetMapping("/cv/list/")
    public String getListOfResumes(Model model) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        PlatformUser platformUser = userService.findUserByLogin(login);
        ArrayList<Resume> resumes = new ArrayList<>(platformUser.getResume());
        model.addAttribute("resumes", resumes);
        model.addAttribute("resumesQuantity", resumes.size());
        if (model.getAttribute("message") == null) {
            model.addAttribute("message", "");
        }
        return "cv-list";
    }

    @GetMapping(value = {"/cv/edit/", "/cv/edit/{id}/"})
    public String getCreatePage(@PathVariable(required = false) Long id, Model model) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        PlatformUser platformUser;
        if (null == id) {
            platformUser = userService.findUserByLogin(login);
            model.addAttribute("resumeOwner", platformUser);
            model.addAttribute("resume", new Resume());
            return "cv-edit";
        } else {
            Resume resume = resumeService.findById(id);
            if (resume != null) {
                platformUser = resume.getPlatformUser();
                if (platformUser.getLogin().equals(login)) {
                    model.addAttribute("resumeOwner", platformUser);
                    model.addAttribute("resume", resume);
                    return "cv-edit";
                } else {
                    model.addAttribute("message", "Ошибка открытия резюме. Доступ запрещен");
                    return "forward:/cv/list/";
                }
            }
            model.addAttribute("message", "Ошибка открытия резюме.");
            return "forward:/cv/list/";
        }
    }

    @PostMapping(value = {"/cv/edit/", "/cv/edit/{id}/"})
    public String saveResume(@PathVariable(required = false) Long id, @ModelAttribute Resume resume, Model model, WebRequest request) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        PlatformUser platformUser = userService.findUserByLogin(login);
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (null == id) {
            resume.setPlatformUser(platformUser);
            if (resumeService.save(resumeService.parametersMappingToResume(parameterMap, resume)) != null) {
                model.addAttribute("message", "Резюме сохранено!");
            } else {
                model.addAttribute("message", "Не сохранилось");
            }
        } else {
            Resume resumeToUpdate = resumeService.findById(id);
            if (resumeToUpdate != null && resumeToUpdate.getPlatformUser().equals(platformUser)) {
                resumeToUpdate.setTitle(resume.getTitle());
                resumeToUpdate.setCommonInfo(resume.getCommonInfo());
                resumeService.save(resumeService.parametersMappingToResume(parameterMap, resumeToUpdate));
                model.addAttribute("message", "Резюме сохранено");
            } else {
                model.addAttribute("message", "Резюме не найдено");
            }
        }
        return "redirect:/cv/list/";
    }

    @ModelAttribute("userCredentials")
    public String populateUserCredentials() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        if ("anonymousUser".equals(login)) {
            return "";
        } else {
            PlatformUser user = userService.findUserByLogin(login);
            return user.getName() +
                    " " +
                    user.getSurname();
        }
    }

}
