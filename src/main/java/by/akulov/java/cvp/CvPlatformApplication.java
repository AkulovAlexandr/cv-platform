package by.akulov.java.cvp;

import by.akulov.java.cvp.model.Photo;
import by.akulov.java.cvp.model.PlatformUser;
import by.akulov.java.cvp.model.Roles;
import by.akulov.java.cvp.model.resume.Resume;
import by.akulov.java.cvp.model.resume.Skill;
import by.akulov.java.cvp.model.resume.contact.Contact;
import by.akulov.java.cvp.model.resume.contact.ContactType;
import by.akulov.java.cvp.model.resume.experience.Experience;
import by.akulov.java.cvp.model.resume.experience.ExperienceType;
import by.akulov.java.cvp.service.ResumeService;
import by.akulov.java.cvp.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@SpringBootApplication
public class CvPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(CvPlatformApplication.class, args);

    }

    @Bean
    CommandLineRunner initTestDatabase(@Value("${spring.profiles.active}") String active, PasswordEncoder passwordEncoder, UserService userService, ResumeService resumeService) {

        return args -> {
            if ("test".equals(active)) {
                PlatformUser user = new PlatformUser();
                user.setRole(String.valueOf(Roles.ROLE_USER));
                user.setLogin("user");
                user.setPassword(passwordEncoder.encode("user"));
                user.setName("Александр");
                user.setSurname("Акулов");
                userService.save(user);

                for (int i = 0; i < 1; i++) {
                    Resume resume = new Resume();
                    resume.setPlatformUser(user);

                    Photo photo = new Photo("test.jpg");


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

                    Experience job1 = new Experience();
                    Experience job2 = new Experience();
                    Experience job3 = new Experience();
                    job1.setTitle("ООО Компания");
                    job1.setDescription("Работал в компании. Выполнял обязаности такие как: первое, второе, третее");
                    job1.setStartYear(2015);
                    job1.setEndYear(2016);
                    job1.setType(ExperienceType.JOB.name());
                    job1.setResume(resume);
                    job2.setTitle("ЧТУП Компания");
                    job2.setDescription("Работал в компании. Выполнял обязаности такие как: первое, второе, третее");
                    job2.setStartYear(2016);
                    job2.setEndYear(2019);
                    job2.setType(ExperienceType.JOB.name());
                    job2.setResume(resume);
                    job3.setTitle("ОАО Компания");
                    job3.setDescription("Работал в компании. Выполнял обязаности такие как: первое, второе, третее");
                    job3.setStartYear(2019);
                    job3.setEndYear(2023);
                    job3.setType(ExperienceType.JOB.name());
                    job3.setResume(resume);

                    Experience edu1 = new Experience();
                    Experience edu2 = new Experience();
                    Experience edu3 = new Experience();
                    edu1.setTitle("Курсы JAVA");
                    edu1.setDescription("Умею немного писать код. Вот этот сайт написал например");
                    edu1.setStartYear(2021);
                    edu1.setEndYear(2023);
                    edu1.setType(ExperienceType.EDUCATION.name());
                    edu1.setResume(resume);
                    edu2.setTitle("Курсы QA");
                    edu2.setDescription("Тестировщик ПО, мануальный");
                    edu2.setStartYear(2021);
                    edu2.setEndYear(2022);
                    edu2.setType(ExperienceType.EDUCATION.name());
                    edu2.setResume(resume);
                    edu3.setTitle("БТЭУПК");
                    edu3.setDescription("Учился в вузе на логиста. Диплом валяется. А, еще магистратуру закончил. Тоже валяется");
                    edu3.setStartYear(2011);
                    edu3.setEndYear(2015);
                    edu3.setType(ExperienceType.EDUCATION.name());
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

                    resume.setTitle("Automation-QA, JAVA-developer");
                    resume.setCommonInfo("Это резюме создано для теста. Тут может быть множество текстов.");
                    resume.setContacts(contacts);
                    resume.setSkills(skills);
                    resume.setJobs(jobs);
                    resume.setEducations(edus);
                    resume.setPlatformUser(user);
                    resume.setPhoto(photo);
                    resumeService.save(resume);
                }
            }
        };

    }

}
