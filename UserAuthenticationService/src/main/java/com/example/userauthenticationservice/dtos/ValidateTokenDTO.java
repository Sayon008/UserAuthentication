package com.example.userauthenticationservice.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter
public class ValidateTokenDTO {
    private String token;
    private Long userId;
}
