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
        return "redirect:/cv/edit/";
    }

    @GetMapping("/cv/edit/")
    public String getCreatePage(Model model) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
       
        model.addAttribute("resume", new Resume());

        return "cv-edit";
    }

    @PostMapping("/cv/edit/")
    public String saveResume(@ModelAttribute Resume resume, Model model, WebRequest request) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, String[]> parameterMap = request.getParameterMap();
        resume.setPlatformUser(userService.findUserByLogin(login));
        if (resumeService.save(parametersMappingToResume(parameterMap, resume)) != null) {
            model.addAttribute("message", "Резюме сохранено!");
        } else {
            model.addAttribute("message", "Не сохранилось");
        }
        return "cv-edit";
    }

    private Resume parametersMappingToResume(Map<String, String[]> map, Resume resume) {

        List<Skill> skillList = new ArrayList<>();
        List<Experience> experienceList = new ArrayList<>();
        List<Contact> contactList = new ArrayList<>();

        List<String> skillKeys = map.keySet().stream()
                .filter(strings -> strings.matches(".*skill.*"))
                .toList();

        List<String> jobKeys = map.keySet().stream()
                .filter(strings -> strings.matches(".*job.*"))
                .toList();

        List<String> educationKeys = map.keySet().stream()
                .filter(strings -> strings.matches(".*edu.*"))
                .toList();

        List<String> contactKeys = map.keySet().stream()
                .filter(strings -> strings.matches(".*contact.*"))
                .toList();

        for (int i = 1; i < skillKeys.size(); i += 2) {
            Skill skill = new Skill();
            skill.setTitle(map.get(skillKeys.get(i - 1))[0]);
            skill.setPercent(Integer.valueOf(map.get(skillKeys.get(i))[0]));
            skill.setResume(resume);
            skillList.add(skill);
        }

        for (int i = 1; i < jobKeys.size(); i += 4) {
            Experience job = new Experience();
            job.setTitle(map.get(jobKeys.get(i - 1))[0]);
            job.setStartYear(Integer.valueOf(map.get(jobKeys.get(i))[0]));
            job.setEndYear(Integer.valueOf(map.get(jobKeys.get(i + 1))[0]));
            job.setDescription(map.get(jobKeys.get(i + 2))[0]);
            job.setType(String.valueOf(ExperienceType.JOB));
            job.setResume(resume);
            experienceList.add(job);
        }

        for (int i = 1; i < educationKeys.size(); i += 4) {
            Experience edu = new Experience();
            edu.setTitle(map.get(educationKeys.get(i - 1))[0]);
            edu.setStartYear(Integer.valueOf(map.get(educationKeys.get(i))[0]));
            edu.setEndYear(Integer.valueOf(map.get(educationKeys.get(i + 1))[0]));
            edu.setDescription(map.get(educationKeys.get(i + 2))[0]);
            edu.setType(String.valueOf(ExperienceType.EDUCATION));
            edu.setResume(resume);
            experienceList.add(edu);
        }

        for (int i = 1; i < contactKeys.size(); i += 2) {
            Contact contact = new Contact();
            contact.setData(map.get(contactKeys.get(i - 1))[0]);
            contact.setType(map.get(contactKeys.get(i))[0]);
            contact.setResume(resume);
            contactList.add(contact);
        }

        resume.setSkills(skillList);
        resume.setContacts(contactList);
        resume.setExperiences(experienceList);
        return resume;
    }

}
