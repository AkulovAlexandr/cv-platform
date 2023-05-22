package by.akulov.java.cvp.repository;

import by.akulov.java.cvp.model.resume.contact.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
    void deleteAllByResume_Id(Long id);
}
