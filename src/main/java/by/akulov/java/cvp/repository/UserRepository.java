package by.akulov.java.cvp.repository;


import by.akulov.java.cvp.model.PlatformUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository extends CrudRepository<PlatformUser, Long> {


    Optional<PlatformUser> findFirstByLogin(String login);

    PlatformUser findUserById(Long id);

}
