package by.akulov.java.cvp.service;

import by.akulov.java.cvp.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserService {

    User getUserById(Long id);

}
