package by.akulov.java.cvp.web;

import by.akulov.java.cvp.model.PlatformUser;
import by.akulov.java.cvp.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String registerForm(Model model) {
        model.addAttribute("user", new PlatformUser());
        return "register";
    }

    @PostMapping("/register/")
    public String registerUser(@ModelAttribute PlatformUser user, @RequestParam String passRepeat, Model model) {
        if (user.getLogin().length() < 5) {
            model.addAttribute("user", new PlatformUser());
            model.addAttribute("loginErr", "Логин должен быть длинее 5 символов");
            return "register";
        }
        if (userService.isExists(user.getLogin())){
            model.addAttribute("user", new PlatformUser());
            model.addAttribute("loginErr", "Пользователь с таким логином уже существует");
            return "register";
        }
        if (user.getPassword().length() < 5) {
            model.addAttribute("user", new PlatformUser());
            model.addAttribute("passErr", "Пароль должен быть длинее 5 символов");
            return "register";
        }
        if (!user.getPassword().equals(passRepeat)){
            model.addAttribute("user", new PlatformUser());
            model.addAttribute("passErr", "Введенные пароли не совпадают");
            return "register";
        }
        if (user.getName().isEmpty()) {
            model.addAttribute("user", new PlatformUser());
            model.addAttribute("nameErr", "Обязательно укажите имя");
            return "register";
        }
        if (user.getSurname().isEmpty()){
            model.addAttribute("user", new PlatformUser());
            model.addAttribute("surnameErr", "Обязательно укажите фамилию");
            return "register";
        }
        user.setPassword(userService.encodePassword(user.getPassword()));
        user.setRole("ROLE_USER");
        userService.save(user);
        return "redirect:/";
    }


    @GetMapping("/login")
    public String login() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        if ("anonymousUser".equals(login)) {
            return "login";
        }
        return "redirect:/";
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
