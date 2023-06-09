package by.akulov.java.cvp.web;

import by.akulov.java.cvp.model.PlatformUser;
import by.akulov.java.cvp.model.Roles;
import by.akulov.java.cvp.model.resume.Resume;
import by.akulov.java.cvp.model.resume.Skill;
import by.akulov.java.cvp.model.resume.contact.Contact;
import by.akulov.java.cvp.model.resume.contact.ContactType;
import by.akulov.java.cvp.model.resume.experience.Education;
import by.akulov.java.cvp.model.resume.experience.Experience;
import by.akulov.java.cvp.model.resume.experience.ExperienceType;
import by.akulov.java.cvp.model.resume.experience.Job;
import by.akulov.java.cvp.repository.UserRepository;
import by.akulov.java.cvp.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {

        PlatformUser user = new PlatformUser();

        Collection<Resume> resumes = new ArrayList<>();
        for (int i = 0; i < 9 ; i++) {
            Resume resume = new Resume();
            resume.setPlatformUser(user);

            Contact email = new Contact();
            email.setResume(resume);
            email.setData("ws.akulov@gmail.com");
            email.setType(String.valueOf(ContactType.EMAIL));

            Contact email2 = new Contact();
            email2.setResume(resume);
            email2.setData("+375 44 533 50 05");
            email2.setType(String.valueOf(ContactType.PHONE));

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

            Experience job1 = new Job();
            Experience job2 = new Job();
            Experience job3 = new Job();
            job1.setTitle("ООО Компания");
            job1.setDescription("Работал в компании. Выполнял обязаности такие как: первое, второе, третее");
            job1.setStartYear(2015);
            job1.setEndYear(2016);
            job1.setResume(resume);
            job2.setTitle("ЧТУП Компания");
            job2.setDescription("Работал в компании. Выполнял обязаности такие как: первое, второе, третее");
            job2.setStartYear(2016);
            job2.setEndYear(2019);
            job2.setResume(resume);
            job3.setTitle("ОАО Компания");
            job3.setDescription("Работал в компании. Выполнял обязаности такие как: первое, второе, третее");
            job3.setStartYear(2019);
            job3.setEndYear(2023);
            job3.setResume(resume);

            Experience edu1 = new Education();
            Experience edu2 = new Education();
            Experience edu3 = new Education();
            edu1.setTitle("Курсы JAVA");
            edu1.setDescription("Умею немного писать код. Вот этот сайт написал например");
            edu1.setStartYear(2021);
            edu1.setEndYear(2023);
            edu1.setResume(resume);
            edu2.setTitle("Курсы QA");
            edu2.setDescription("Тестировщик ПО, мануальный");
            edu2.setStartYear(2021);
            edu2.setEndYear(2022);
            edu2.setResume(resume);
            edu3.setTitle("БТЭУПК");
            edu3.setDescription("Учился в вузе на логиста. Диплом валяется. А, еще магистратуру закончил. Тоже валяется");
            edu3.setStartYear(2011);
            edu3.setEndYear(2015);
            edu3.setResume(resume);

            Collection<Experience> jobs = new ArrayList<>();
            Collection<Experience> edus = new ArrayList<>();
            jobs.add(job1);
            jobs.add(job2);
            jobs.add(job3);
            edus.add(edu1);
            edus.add(edu2);
            edus.add(edu3);

            Collection<Contact> contacts = new ArrayList<>();
            contacts.add(email);
            contacts.add(email2);
            contacts.add(link);

            Collection<Skill> skills = new ArrayList<>();
            skills.add(firstSkill);
            skills.add(secondSkill);
            skills.add(thirdSkill);

            for (int j = 0; j < 2; j++) {
                Skill skill = new Skill();
                skill.setResume(resume);
                skill.setTitle("TEST");
                skill.setPercent(99);
                skills.add(skill);
            }

            resume.setTitle("Сварщик, блогер, оптимист");
            resume.setCommonInfo("Я создал это резюме для теста. Тут может быть множество текстов.");
            resume.setContacts(contacts);
            resume.setSkills(skills);
            resume.setJobs(jobs);
            resume.setEducations(edus);
            resumes.add(resume);
        }


        user.setRole(String.valueOf(Roles.ROLE_USER));
        user.setLogin("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setName("Александр");
        user.setSurname("Акулов");
        user.setResume(resumes);

        userService.save(user);

    }


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @ModelAttribute("userCredentials")
    public String populateUserCredentials() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        if ("anonymousUser".equals(login)) {
            return "anon";
        } else {
            PlatformUser user = userService.findUserByLogin(login);
            return user.getName() +
                    " " +
                    user.getSurname();
        }
    }

}
