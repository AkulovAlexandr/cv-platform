package by.akulov.java.cvp.service;

import by.akulov.java.cvp.model.resume.Resume;

import java.util.ArrayList;
import java.util.Map;

public interface ResumeService {

    Resume save(Resume resume);
    Resume update(Resume resume);
    Resume findById(Long id);
    void deleteResume(Resume resume);
    Resume parametersMappingToResume(Map<String, String[]> parameterMap, Resume resume);

}
