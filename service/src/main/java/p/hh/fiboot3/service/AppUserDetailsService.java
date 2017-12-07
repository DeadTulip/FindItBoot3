package p.hh.fiboot3.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import p.hh.fiboot3.dao.UserDao;
import p.hh.fiboot3.domain.User;
import p.hh.fiboot3.dto.UserDto;
import p.hh.fiboot3.security.AppUserPrincipal;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        System.out.println("loadUserByUsername " + username);
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new AppUserPrincipal(user);
    }

    public UserDto getCurrentUser() {
        User user = ((AppUserPrincipal)SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal()).getAppUser();
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto getUser(Authentication authentication) {
        User user = ((AppUserPrincipal)authentication.getPrincipal()).getAppUser();
        return modelMapper.map(user, UserDto.class);
    }
}
