package by.akulov.java.cvp.service;

import by.akulov.java.cvp.model.resume.Resume;
import by.akulov.java.cvp.model.resume.Skill;
import by.akulov.java.cvp.model.resume.contact.Contact;
import by.akulov.java.cvp.model.resume.experience.Education;
import by.akulov.java.cvp.model.resume.experience.Experience;
import by.akulov.java.cvp.model.resume.experience.ExperienceType;
import by.akulov.java.cvp.model.resume.experience.Job;
import by.akulov.java.cvp.repository.ContactRepository;
import by.akulov.java.cvp.repository.ExperienceRepository;
import by.akulov.java.cvp.repository.ResumeRepository;
import by.akulov.java.cvp.repository.SkillRepository;
import jakarta.transaction.Transactional;
import org.apache.tomcat.Jar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private ExperienceRepository experienceRepository;

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

        for (int i = 1; i < skillKeys.size(); i += 2) {
            Skill skill = new Skill();
            skill.setTitle(map.get(skillKeys.get(i - 1))[0]);
            skill.setPercent(Integer.valueOf(map.get(skillKeys.get(i))[0]));
            skill.setResume(resume);
            skillList.add(skill);
        }

        for (int i = 1; i < jobKeys.size(); i += 4) {
            Experience job = new Job();
            job.setTitle(map.get(jobKeys.get(i - 1))[0]);
            job.setStartYear(Integer.valueOf(map.get(jobKeys.get(i))[0]));
            job.setEndYear(Integer.valueOf(map.get(jobKeys.get(i + 1))[0]));
            job.setDescription(map.get(jobKeys.get(i + 2))[0]);
            job.setResume(resume);
            jobsList.add(job);
        }

        for (int i = 1; i < educationKeys.size(); i += 4) {
            Experience edu = new Education();
            edu.setTitle(map.get(educationKeys.get(i - 1))[0]);
            edu.setStartYear(Integer.valueOf(map.get(educationKeys.get(i))[0]));
            edu.setEndYear(Integer.valueOf(map.get(educationKeys.get(i + 1))[0]));
            edu.setDescription(map.get(educationKeys.get(i + 2))[0]);
            edu.setResume(resume);
            educationsList.add(edu);
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
        resume.setJobs(jobsList);
        resume.setEducations(educationsList);
        return resume;
    }


    @Override
    public Resume save(Resume resume) {
        return resumeRepository.save(resume);
    }

    @Override
    public ArrayList<Resume> findAllByUserId(Long id) {
        return resumeRepository.findAllByPlatformUser_id(id);
    }

    @Override
    public Resume findById(Long id) {
        return resumeRepository.findFirstById(id);
    }

    @Override
    public void deleteResumeById(Long id) {
        Resume resume = resumeRepository.findFirstById(id);
        contactRepository.deleteAllByResume_id(id);
        skillRepository.deleteAllByResume_id(id);
        experienceRepository.deleteAllByResume_id(id);
        resumeRepository.delete(resume);
    }


}
