package by.akulov.java.cvp.service;

import by.akulov.java.cvp.model.PlatformUser;
import by.akulov.java.cvp.model.resume.Resume;
import by.akulov.java.cvp.model.resume.Skill;
import by.akulov.java.cvp.model.resume.contact.Contact;
import by.akulov.java.cvp.model.resume.experience.Experience;
import by.akulov.java.cvp.model.resume.experience.ExperienceType;
import by.akulov.java.cvp.repository.ContactRepository;
import by.akulov.java.cvp.repository.ExperienceRepository;
import by.akulov.java.cvp.repository.ResumeRepository;
import by.akulov.java.cvp.repository.SkillRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Override
    public Resume parametersMappingToResume(Map<String, String[]> map, Resume resume) {

        List<Skill> skillList = new ArrayList<>();
        List<Experience> jobsList = new ArrayList<>();
        List<Experience> educationsList = new ArrayList<>();
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

        for (int i = 1; i < skillKeys.size(); i += 3) {
            Skill skill = new Skill();
            String id = map.get(skillKeys.get(i - 1))[0];
            skill.setId(id.isEmpty() ? null : Long.parseLong(id));
            skill.setTitle(map.get(skillKeys.get(i))[0]);
            skill.setPercent(Integer.parseInt(map.get(skillKeys.get(i + 1))[0]));
            skill.setResume(resume);
            skillList.add(skill);
        }

        for (int i = 1; i < jobKeys.size(); i += 5) {
            Experience job = new Experience();
            String id = map.get(jobKeys.get(i - 1))[0];
            job.setId(id.isEmpty() ? null : Long.parseLong(id));
            job.setTitle(map.get(jobKeys.get(i))[0]);
            job.setStartYear(Integer.parseInt(map.get(jobKeys.get(i + 1))[0]));
            job.setEndYear(Integer.parseInt(map.get(jobKeys.get(i + 2))[0]));
            job.setDescription(map.get(jobKeys.get(i + 3))[0]);
            job.setType(ExperienceType.JOB.name());
            job.setResume(resume);
            jobsList.add(job);
        }

        for (int i = 1; i < educationKeys.size(); i += 5) {
            Experience edu = new Experience();
            String id = map.get(educationKeys.get(i - 1))[0];
            edu.setId(id.isEmpty() ? null : Long.parseLong(id));
            edu.setTitle(map.get(educationKeys.get(i))[0]);
            edu.setStartYear(Integer.parseInt(map.get(educationKeys.get(i + 1))[0]));
            edu.setEndYear(Integer.parseInt(map.get(educationKeys.get(i + 2))[0]));
            edu.setDescription(map.get(educationKeys.get(i + 3))[0]);
            edu.setType(ExperienceType.EDUCATION.name());
            edu.setResume(resume);
            educationsList.add(edu);
        }

        for (int i = 1; i < contactKeys.size(); i += 3) {
            Contact contact = new Contact();
            String id = map.get(contactKeys.get(i - 1))[0];
            contact.setId(id.isEmpty() ? null : Long.parseLong(id));
            contact.setData(map.get(contactKeys.get(i))[0]);
            contact.setType(map.get(contactKeys.get(i + 1))[0]);
            contact.setResume(resume);
            contactList.add(contact);
        }

        resume.setSkills(skillList);
        resume.setContacts(contactList);
        resume.setJobs(jobsList);
        resume.setEducations(educationsList);
        return resume;
    }



    @Override
    public Resume save(Resume resume) {
        Resume saved = resumeRepository.save(resume);
        skillRepository.saveAll(resume.getSkills());
        contactRepository.saveAll(resume.getContacts());
        experienceRepository.saveAll(resume.getExperiences());
        return saved;
    }

    @Override
    @Transactional
    public Resume update(Resume resume) {
        Resume saved = resumeRepository.save(resume);
        skillRepository.deleteAllByResume_Id(resume.getId());
        contactRepository.deleteAllByResume_Id(resume.getId());
        experienceRepository.deleteAllByResume_Id(resume.getId());
        skillRepository.saveAll(resume.getSkills());
        contactRepository.saveAll(resume.getContacts());
        experienceRepository.saveAll(resume.getExperiences());
        return saved;
    }

    @Override
    public Resume findById(Long id) {
        return resumeRepository.findFirstById(id);
    }

    @Override
    public void deleteResume(Resume resume) {
        skillRepository.deleteAll(resume.getSkills());
        experienceRepository.deleteAll(resume.getExperiences());
        contactRepository.deleteAll(resume.getContacts());
        resumeRepository.delete(resume);
    }


}
