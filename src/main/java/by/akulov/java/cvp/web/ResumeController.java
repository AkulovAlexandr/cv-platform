package by.akulov.java.cvp.web;

import by.akulov.java.cvp.model.PlatformUser;
import by.akulov.java.cvp.model.resume.Resume;
import by.akulov.java.cvp.service.ResumeService;
import by.akulov.java.cvp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.*;


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
//                model.addAttribute("message", "Ошибка открытия резюме. Доступ запрещен");
                return "404";
            }
        }
//        model.addAttribute("message", "Ошибка открытия резюме");
        return "404";
    }

    @GetMapping("/cv/list/")
    public String getListOfResumes(Model model) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        PlatformUser platformUser = userService.findUserByLogin(login);
        ArrayList<Resume> resumes = new ArrayList<>(platformUser.getResumes());
        model.addAttribute("resumes", resumes);
        if (model.getAttribute("message") == null) {
            model.addAttribute("message", "");
        }
        return "cv-list";
    }

    @GetMapping("/cv/delete/{id}/")
    public String deleteResume(@PathVariable Long id, Model model) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        PlatformUser user = userService.findUserByLogin(login);
        Resume resume = resumeService.findById(id);
        if (null != resume && resume.getPlatformUser().equals(user)) {
            resumeService.deleteResume(resume);
            model.addAttribute("message", "Резюме удалено!");
            model.addAttribute("resumes", user.getResumes());
            return "cv-list";
        }
        model.addAttribute("resumes", user.getResumes());
        model.addAttribute("message", "Нет доступа к запрашиваемому резюме!");
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
            if (null != resume) {
                platformUser = resume.getPlatformUser();
                if (platformUser.getLogin().equals(login)) {
                    model.addAttribute("resumeOwner", platformUser);
                    model.addAttribute("resume", resume);
                    return "cv-edit";
                } else {
//                    model.addAttribute("message", "Ошибка открытия резюме. Доступ запрещен");
                    return "404";
                }
            }
//            model.addAttribute("message", "Ошибка открытия резюме.");
            return "404";
        }
    }

    @PostMapping(value = {"/cv/edit/"})
    public String saveResume(@RequestParam(required = false) Long id, @ModelAttribute Resume resume, Model model, WebRequest request) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        PlatformUser user = userService.findUserByLogin(login);
        Map<String, String[]> parameterMap = request.getParameterMap();
        resume.setPlatformUser(user);
        if (null == id) {
            resumeService.save(resumeService.parametersMappingToResume(parameterMap, resume));
            model.addAttribute("message", "Резюме сохранено!");
        } else {
            resumeService.update(resumeService.parametersMappingToResume(parameterMap, resume));
            model.addAttribute("message", "Резюме обновлено!");
        }
        model.addAttribute("resumes", user.getResumes());
        return "cv-list";
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
