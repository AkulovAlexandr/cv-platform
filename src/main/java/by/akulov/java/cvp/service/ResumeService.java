package by.akulov.java.cvp.service;

import by.akulov.java.cvp.model.Resume;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface ResumeService {

    Resume save(Resume resume);

    ArrayList<Resume> findAllByUserId(Long id);

    Resume findById(Long id);

    void deleteResumeById(Long id);

}
