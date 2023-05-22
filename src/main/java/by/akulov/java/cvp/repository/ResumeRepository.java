package by.akulov.java.cvp.repository;

import by.akulov.java.cvp.model.resume.Resume;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends CrudRepository<Resume, Long> {


    Resume findFirstById(Long id);


}
