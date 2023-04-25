package by.akulov.java.cvp.service;

import by.akulov.java.cvp.model.resume.Resume;

import java.util.ArrayList;

public interface ResumeService {

    Resume save(Resume resume);

    ArrayList<Resume> findAllByUserId(Long id);

    Resume findById(Long id);

    void deleteResumeById(Long id);

}
