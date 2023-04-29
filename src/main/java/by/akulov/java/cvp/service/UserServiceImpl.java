package by.akulov.java.cvp.service;

import by.akulov.java.cvp.model.PlatformUser;
import by.akulov.java.cvp.model.Roles;
import by.akulov.java.cvp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public PlatformUser getUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public PlatformUser findUserByLogin(String login) {
        return userRepository
                .findFirstByLogin(login)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Пользователь с именем \"" + login + "\" - не найден"));
    }

    @Override
    public void save(PlatformUser user) {
        userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PlatformUser platformUser = findUserByLogin(username);
        List<Roles> roles = new ArrayList<>();
        roles.add(Roles.valueOf(platformUser.getRole()));
        return new User(platformUser.getLogin(), platformUser.getPassword(), mapToGrantedAuthorities(roles));
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Roles> roles) {
        return roles.stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }


}
