package by.akulov.java.cvp.service;

import by.akulov.java.cvp.model.User;
import by.akulov.java.cvp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public User getUserById(Long id) {
        return userRepository.findUserById(id);
    }
}
