package by.akulov.java.cvp.repository;


import by.akulov.java.cvp.model.resume.experience.Experience;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceRepository extends CrudRepository<Experience, Long> {
    void deleteAllByResume_Id(Long id);
}
