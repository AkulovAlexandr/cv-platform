package by.akulov.java.cvp.web;

import by.akulov.java.cvp.model.PlatformUser;
import by.akulov.java.cvp.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/register/")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register/")
    public String registerUser() {
        return "register";
    }




    @ModelAttribute("userCredentials")
    public String populateUserCredentials() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        if ("anonymousUser".equals(login)) {
            return "anon";
        } else {
            PlatformUser user = userService.findUserByLogin(login);
            return user.getName() +
                    " " +
                    user.getSurname();
        }
    }

}
