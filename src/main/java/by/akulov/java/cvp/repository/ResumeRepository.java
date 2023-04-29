package by.akulov.java.cvp.repository;

import by.akulov.java.cvp.model.resume.Resume;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
public interface ResumeRepository extends CrudRepository<Resume, Long> {

    ArrayList<Resume> findAllByPlatformUser_id(Long id);

    Resume findFirstById(Long id);


}
