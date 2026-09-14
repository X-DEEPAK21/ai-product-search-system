package com.ai.AiSearch.requestDto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDto {
    Long userId;
    String refreshToken;
    String accessToken;
}
