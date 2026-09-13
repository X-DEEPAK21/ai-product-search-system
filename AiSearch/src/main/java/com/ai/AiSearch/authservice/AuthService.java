package com.ai.AiSearch.authservice;

import com.ai.AiSearch.entity.RealUser;
import com.ai.AiSearch.entity.Role;
import com.ai.AiSearch.exception.EmailAlreadyExistsException;
import com.ai.AiSearch.repository.UserRepository;
import com.ai.AiSearch.requestDto.RegistrationRequestDto;
import com.ai.AiSearch.responseDto.RegistrationResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
                .message("User registered successfully")
                .build();
    }
}
