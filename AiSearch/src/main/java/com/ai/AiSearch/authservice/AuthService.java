package com.ai.AiSearch.authservice;

import com.ai.AiSearch.entity.RealUser;
import com.ai.AiSearch.entity.Role;
import com.ai.AiSearch.exception.EmailAlreadyExistsException;
import com.ai.AiSearch.repository.UserRepository;
import com.ai.AiSearch.requestDto.LoginRequestDto;
import com.ai.AiSearch.requestDto.LoginResponseDto;
import com.ai.AiSearch.requestDto.RegistrationRequestDto;
import com.ai.AiSearch.responseDto.RegistrationResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final  Token jwttoken;

    @Transactional
    public RegistrationResponseDto register(RegistrationRequestDto request) {

        log.info("Starting user registration for email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {

            log.warn("Registration failed. Email already exists: {}",
                    request.getEmail());

            throw new EmailAlreadyExistsException(
                    "User already exists with email: " + request.getEmail()
            );
        }

        RealUser user = RealUser.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .gender(request.getGender())
                .role(Role.USER)
                .isActive(true)
                .isVerified(false)
                .build();

         RealUser savedUser = userRepository.save(user);

        log.info("User registered successfully. UserId: {}",
                savedUser.getId());

        return RegistrationResponseDto.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .gender(savedUser.getGender())
                .message("User registered successfully, Lets redirect to Login")
                .build();
    }





    public LoginResponseDto login(LoginRequestDto loginRequestDto){
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),loginRequestDto.getPassword());
        Authentication authentication=authenticationManager.authenticate(token);
        RealUser realUser =((UserDetailsImpl)authentication.getPrincipal()).getRealUser();

        String accessToken=jwttoken.generateAccessToken(realUser);
        String refreshToken=jwttoken.generateRefreshToken(realUser);
        return LoginResponseDto.builder().userId(realUser.getId())
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
    }

    public LoginResponseDto refreshToken(String refreshToken) {
        Long userId = jwttoken.getUserIdFromToken(refreshToken);
        RealUser realUser =userRepository.findById(userId).get();
        String accessToken = jwttoken.generateAccessToken(realUser);

        return LoginResponseDto.builder().userId(realUser.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken).build();
    }
}
