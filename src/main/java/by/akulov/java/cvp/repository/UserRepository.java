package by.akulov.java.cvp.repository;


import by.akulov.java.cvp.model.PlatformUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<PlatformUser, Long> {


    Optional<PlatformUser> findFirstByLogin(String login);

    PlatformUser findUserById(Long id);

}
