package by.akulov.java.cvp.repository;

import by.akulov.java.cvp.model.resume.Resume;
import by.akulov.java.cvp.model.resume.contact.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {

    void deleteAllByResume_id(Long id);
}
