package com.ai.AiSearch.authservice;

import com.ai.AiSearch.entity.RealUser;
import com.ai.AiSearch.userservice.UserService;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class LoginDetail implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       RealUser  realUser= userService.findByEmail(username);
        if(realUser.isActive()==false){
            throw new LockedException("User is Blocked");
        }
        return new UserDetailsImpl(realUser);
    }
}
