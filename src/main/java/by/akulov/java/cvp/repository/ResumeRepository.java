package by.akulov.java.cvp.repository;

import by.akulov.java.cvp.model.resume.Resume;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public interface ResumeRepository extends CrudRepository<Resume, Long> {

    ArrayList<Resume> findAllByPlatformUser_id(Long id);

    Resume findFirstById(Long id);


}
