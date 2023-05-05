package by.akulov.java.cvp.service;

import by.akulov.java.cvp.model.resume.Resume;
import by.akulov.java.cvp.model.resume.Skill;
import by.akulov.java.cvp.model.resume.contact.Contact;
import by.akulov.java.cvp.model.resume.experience.Experience;
import by.akulov.java.cvp.model.resume.experience.ExperienceType;
import by.akulov.java.cvp.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;

    private final ExperienceRepository experienceRepository;

    private final ContactRepository contactRepository;

    private final SkillRepository skillRepository;

    @Autowired
    private PhotoRepository photoRepository;

    public ResumeServiceImpl(ResumeRepository resumeRepository, ExperienceRepository experienceRepository, ContactRepository contactRepository, SkillRepository skillRepository) {
        this.resumeRepository = resumeRepository;
        this.experienceRepository = experienceRepository;
        this.contactRepository = contactRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public Resume parametersMappingToResume(Map<String, String[]> map, Resume resume) {

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


        List<Skill> skillList = new ArrayList<>();
        for (int i = 1; i < skillKeys.size(); i += 3) {
            Skill skill = new Skill();
            String id = map.get(skillKeys.get(i - 1))[0];
            skill.setId(id.isEmpty() ? null : Long.parseLong(id));
            skill.setTitle(map.get(skillKeys.get(i))[0]);
            skill.setPercent(Integer.parseInt(map.get(skillKeys.get(i + 1))[0]));
            skill.setResume(resume);
            skillList.add(skill);
        }

        List<Contact> contactList = new ArrayList<>();
        for (int i = 1; i < contactKeys.size(); i += 3) {
            Contact contact = new Contact();
            String id = map.get(contactKeys.get(i - 1))[0];
            contact.setId(id.isEmpty() ? null : Long.parseLong(id));
            contact.setData(map.get(contactKeys.get(i))[0]);
            contact.setType(map.get(contactKeys.get(i + 1))[0]);
            contact.setResume(resume);
            contactList.add(contact);
        }

        List<Experience> jobsList = createExperiences(map, jobKeys, resume, ExperienceType.JOB);
        List<Experience> educationsList = createExperiences(map, educationKeys, resume, ExperienceType.EDUCATION);

        resume.setSkills(skillList);
        resume.setContacts(contactList);
        resume.setJobs(jobsList);
        resume.setEducations(educationsList);
        return resume;
    }


    private List<Experience> createExperiences(Map<String, String[]> map, List<String> keys, Resume resume, ExperienceType type) {
        List<Experience> experiences = new ArrayList<>();
        for (int i = 1; i < keys.size(); i += 5) {
            Experience exp = new Experience();
            String id = map.get(keys.get(i - 1))[0];
            exp.setId(id.isEmpty() ? null : Long.parseLong(id));
            exp.setTitle(map.get(keys.get(i))[0]);
            exp.setStartYear(Integer.parseInt(map.get(keys.get(i + 1))[0]));
            exp.setEndYear(Integer.parseInt(map.get(keys.get(i + 2))[0]));
            exp.setDescription(map.get(keys.get(i + 3))[0]);
            exp.setType(type.name());
            exp.setResume(resume);
            experiences.add(exp);
        }
        return experiences;
    }

    @Override
    public Resume save(Resume resume) {
        if (resume.getPhoto() != null) {
            photoRepository.save(resume.getPhoto());
        }
        Resume saved = resumeRepository.save(resume);
        skillRepository.saveAll(resume.getSkills());
        contactRepository.saveAll(resume.getContacts());
        experienceRepository.saveAll(resume.getExperiences());
        return saved;
    }

    @Override
    @Transactional
    public Resume update(Resume resume) {
        if (resume.getPhoto() != null) {
            photoRepository.save(resume.getPhoto());
        }
        skillRepository.deleteAllByResume_Id(resume.getId());
        contactRepository.deleteAllByResume_Id(resume.getId());
        experienceRepository.deleteAllByResume_Id(resume.getId());
        Resume saved = resumeRepository.save(resume);
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
