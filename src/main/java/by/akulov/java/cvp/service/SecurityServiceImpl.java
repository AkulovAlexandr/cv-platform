package by.akulov.java.cvp.service;

import by.akulov.java.cvp.repository.UserRepository;
import by.akulov.java.cvp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User isAuthenticated(String login, String password) {
        User searchedUser = new User();
        searchedUser.setPassword(password);
        searchedUser.setLogin(login);
        User findedUser = userRepository.findFirstByLogin(login);
        return searchedUser.equals(findedUser) ? findedUser : null;
    }
}
