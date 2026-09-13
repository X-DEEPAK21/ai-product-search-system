package com.ai.AiSearch.controller;

i
import com.ai.AiSearch.requestDto.RegistrationRequestDto;
import com.ai.AiSearch.responseDto.RegistrationResponseDto;
import com.ai.AiSearch.authservice.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDto> createUser(@Valid @RequestBody RegistrationRequestDto request){
        log.info("Received registration request for email: {}",
                request.getEmail());
        RegistrationResponseDto response = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
