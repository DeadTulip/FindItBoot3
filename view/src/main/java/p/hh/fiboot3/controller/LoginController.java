package p.hh.fiboot3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import p.hh.fiboot3.service.AppUserDetailsService;

@Controller
@RequestMapping("/")
public class LoginController {
    @Autowired
    private AppUserDetailsService appUserDetailsService;

    @GetMapping("/login")
    public String login(ModelMap model) {
        model.addAttribute("username", "unknown yet");
        return "login/login";
    }

    @GetMapping("/welcome")
    public String gotoWelcome(ModelMap model) {
        return "login/welcome";
    }
}
