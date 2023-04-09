package by.akulov.java.cvp.service;

import by.akulov.java.cvp.model.User;

public interface SecurityService {

    User isAuthenticated(String login, String password);

}
