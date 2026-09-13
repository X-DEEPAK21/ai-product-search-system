package com.ai.AiSearch.responseDto;

import com.ai.AiSearch.entity.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegistrationResponseDto {
    private Long id;
    private String name;
    private String email;
    private Gender gender;
    private String message;
}
