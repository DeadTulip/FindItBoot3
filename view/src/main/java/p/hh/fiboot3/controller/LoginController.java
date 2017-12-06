package p.hh.fiboot3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @GetMapping("/welcome")
    public String gotoWelcome() {
        return "login/welcome";
    }
}
