package by.akulov.java.cvp.repository;

import by.akulov.java.cvp.model.Photo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends CrudRepository<Photo, Long> {
}
