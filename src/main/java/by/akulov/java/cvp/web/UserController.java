package by.akulov.java.cvp.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {


    @GetMapping("/")
    public String index(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String surname = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        model.addAttribute("name", name);
        model.addAttribute("surname", surname);
        return "index";
    }
//
//    @GetMapping("/login")
//    public String login(Model model) {
//        return "login";
//    }

}
