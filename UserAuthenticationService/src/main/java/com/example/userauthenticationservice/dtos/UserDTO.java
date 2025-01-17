package com.example.userauthenticationservice.dtos;

import com.example.userauthenticationservice.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String email;
    private List<Role> roles = new ArrayList<>();
}
