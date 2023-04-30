package by.akulov.java.cvp.repository;

import by.akulov.java.cvp.model.resume.Resume;
import by.akulov.java.cvp.model.resume.Skill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface SkillRepository extends CrudRepository<Skill, Long> {
    void deleteAllByResume_Id(Long id);

}
