package by.akulov.java.cvp.repository;

import by.akulov.java.cvp.model.resume.Resume;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface ResumeRepository extends Repository<Resume, Long> {

    Resume save(Resume resume);

    ArrayList<Resume> findAllByPlatformUser_id(Long id);

    Resume findFirstById(Long id);

    Integer removeResumeById(Long id);

    @Modifying
    @Query("delete from Resume r where r.id=:id")
    void deleteById(@Param("id") Long id);


}
