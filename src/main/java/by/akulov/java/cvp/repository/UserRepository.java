package by.akulov.java.cvp.repository;


import by.akulov.java.cvp.model.Resume;
import by.akulov.java.cvp.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;


public interface UserRepository extends CrudRepository<User, Long> {

    User findFirstByName(String name);

    User findFirstByLogin(String login);

    User findUserById(Long id);
}
