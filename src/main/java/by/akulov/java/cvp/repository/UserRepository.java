package by.akulov.java.cvp.repository;


import by.akulov.java.cvp.model.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {

    User findFirstByName(String name);

    User findFirstByLogin(String login);
}
