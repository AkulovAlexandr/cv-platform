package by.akulov.java.cvp.service;

import by.akulov.java.cvp.model.resume.Resume;
import by.akulov.java.cvp.repository.ResumeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ResumeServiceImpl implements ResumeService{

    @Autowired
    private ResumeRepository resumeRepository;


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
    @Transactional
    public void deleteResumeById(Long id) {
        resumeRepository.deleteById(id);
    }


}
