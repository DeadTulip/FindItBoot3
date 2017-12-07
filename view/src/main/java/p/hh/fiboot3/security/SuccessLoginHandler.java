package p.hh.fiboot3.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import p.hh.fiboot3.domain.User;
import p.hh.fiboot3.dto.UserDto;
import p.hh.fiboot3.service.AppUserDetailsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SuccessLoginHandler implements AuthenticationSuccessHandler {

    @Autowired
    private AppUserDetailsService appUserDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication)
            throws IOException, ServletException {
        UserDto user = appUserDetailsService.getUser(authentication);
        httpServletRequest.getSession().setAttribute("user", user);
    }
}
