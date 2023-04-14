package by.akulov.java.cvp.web;

import by.akulov.java.cvp.model.User;
import by.akulov.java.cvp.service.SecurityService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private SecurityService securityService;


    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        if (session.getAttribute("user") != null){
            return "index";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String password, Model model, HttpSession session) {
        User user = securityService.isAuthenticated(login, password);
        if (user != null) {
//            session.setAttribute("userName", user.getName());
//            session.setAttribute("userSurName", user.getSurname());
//            model.addAttribute("userName", user.getName());
//            model.addAttribute("userSurName", user.getSurname());
            model.addAttribute("user", user);
            session.setAttribute("user", user);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Your username or password is invalid.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
