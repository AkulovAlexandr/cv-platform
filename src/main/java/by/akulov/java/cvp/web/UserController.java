package by.akulov.java.cvp.web;

import by.akulov.java.cvp.model.PlatformUser;
import by.akulov.java.cvp.model.Roles;
import by.akulov.java.cvp.model.resume.Resume;
import by.akulov.java.cvp.model.resume.Skill;
import by.akulov.java.cvp.model.resume.contact.Contact;
import by.akulov.java.cvp.model.resume.contact.ContactType;
import by.akulov.java.cvp.model.resume.experience.Experience;
import by.akulov.java.cvp.model.resume.experience.ExperienceType;
import by.akulov.java.cvp.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collection;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        Resume resume = new Resume();
        PlatformUser user = new PlatformUser();
        resume.setPlatformUser(user);

        Contact email = new Contact();
        email.setResume(resume);
        email.setData("ws.akulov@gmail.com");
        email.setType(String.valueOf(ContactType.EMAIL));

        Contact email2 = new Contact();
        email2.setResume(resume);
        email2.setData("+375 44 533 50 05");
        email2.setType(String.valueOf(ContactType.EMAIL));

        Contact link = new Contact();
        link.setResume(resume);
        link.setData("https://onliner.by");
        link.setType(String.valueOf(ContactType.LINK));

        Skill firstSkill = new Skill();
        firstSkill.setResume(resume);
        firstSkill.setTitle("JS");
        firstSkill.setPercent(40);

        Skill secondSkill = new Skill();
        secondSkill.setResume(resume);
        secondSkill.setTitle("Java");
        secondSkill.setPercent(80);

        Skill thirdSkill = new Skill();
        thirdSkill.setResume(resume);
        thirdSkill.setTitle("PHP");
        thirdSkill.setPercent(10);

        Experience job1 = new Experience();
        Experience job2 = new Experience();
        Experience job3 = new Experience();
        job1.setTitle("ООО Компания");
        job1.setDescription("Работал в компании. Выполнял обязаности такие как: первое, второе, третее");
        job1.setStartYear(2015);
        job1.setEndYear(2016);
        job1.setType(String.valueOf(ExperienceType.JOB));
        job1.setResume(resume);
        job2.setTitle("ЧТУП Компания");
        job2.setDescription("Работал в компании. Выполнял обязаности такие как: первое, второе, третее");
        job2.setStartYear(2016);
        job2.setEndYear(2019);
        job2.setType(String.valueOf(ExperienceType.JOB));
        job2.setResume(resume);
        job3.setTitle("ОАО Компания");
        job3.setDescription("Работал в компании. Выполнял обязаности такие как: первое, второе, третее");
        job3.setStartYear(2019);
        job3.setEndYear(2023);
        job3.setType(String.valueOf(ExperienceType.JOB));
        job3.setResume(resume);

        Experience edu1 = new Experience();
        Experience edu2 = new Experience();
        Experience edu3 = new Experience();
        edu1.setTitle("Курсы JAVA");
        edu1.setDescription("Умею немного писать код. Вот этот сайт написал например");
        edu1.setStartYear(2021);
        edu1.setEndYear(2023);
        edu1.setType(String.valueOf(ExperienceType.EDUCATION));
        edu1.setResume(resume);
        edu2.setTitle("Курсы QA");
        edu2.setDescription("Тестировщик ПО, мануальный");
        edu2.setStartYear(2021);
        edu2.setEndYear(2022);
        edu2.setType(String.valueOf(ExperienceType.EDUCATION));
        edu2.setResume(resume);
        edu3.setTitle("БТЭУПК");
        edu3.setDescription("Учился в вузе на логиста. Диплом валяется. А, еще магистратуру закончил. Тоже валяется");
        edu3.setStartYear(2011);
        edu3.setEndYear(2015);
        edu3.setType(String.valueOf(ExperienceType.EDUCATION));
        edu3.setResume(resume);

        Collection<Experience> experiences = new ArrayList<>();
        experiences.add(job1);
        experiences.add(job2);
        experiences.add(job3);
        experiences.add(edu1);
        experiences.add(edu2);
        experiences.add(edu3);

        Collection<Contact> contacts = new ArrayList<>();
        contacts.add(email);
        contacts.add(email2);
        contacts.add(link);

        Collection<Skill> skills = new ArrayList<>();
        skills.add(firstSkill);
        skills.add(secondSkill);
        skills.add(thirdSkill);

        for (int i = 0; i < 2; i++) {
            Skill skill = new Skill();
            skill.setResume(resume);
            skill.setTitle("TEST");
            skill.setPercent(66);
            skills.add(skill);
        }

        resume.setTitle("Сварщик, блогер, оптимист");
        resume.setCommonInfo("Я создал это резюме для теста. Тут может быть множество текстов.");
        resume.setWorkExperienceInfo("Работа 1");
        resume.setEducationInfo("Учеба 1");
        resume.setContacts(contacts);
        resume.setSkills(skills);
        resume.setExperiences(experiences);

        Collection<Resume> resumes = new ArrayList<>();
        resumes.add(resume);

        user.setRole(String.valueOf(Roles.ROLE_USER));
        user.setLogin("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setName("Alexandr");
        user.setSurname("Akulov");
        user.setResume(resumes);

        userRepository.save(user);


    }


    @GetMapping("/")
    public String index(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String surname = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        model.addAttribute("name", name);
        model.addAttribute("surname", surname);
        return "index";
    }
//
//    @GetMapping("/login")
//    public String login(Model model) {
//        return "login";
//    }

}
