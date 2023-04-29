package by.akulov.java.cvp.service;

import by.akulov.java.cvp.model.PlatformUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    PlatformUser getUserById(Long id);

    PlatformUser findUserByLogin(String login);

    void save(PlatformUser user);
}
