package com.ai.AiSearch.userservice;

import com.ai.AiSearch.entity.RealUser;
import com.ai.AiSearch.exception.EmailNotFoundException;
import com.ai.AiSearch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

   private final UserRepository userRepository;


   public RealUser findByEmail(String email) {
       Optional<RealUser> realUser = userRepository.findByEmail(email);
       if(realUser.isEmpty()){
           throw new EmailNotFoundException("Email not found");
       }
       return realUser.get();

   }

    public RealUser findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + id));
    }


}
