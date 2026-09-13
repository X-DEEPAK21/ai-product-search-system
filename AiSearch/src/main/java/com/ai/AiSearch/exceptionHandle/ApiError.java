package com.ai.AiSearch.exceptionHandle;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
           Integer status;
             String message;
            LocalDateTime timestamp;
}
