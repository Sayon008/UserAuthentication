package com.example.userauthenticationservice.service;

import com.example.userauthenticationservice.exception.PasswordMissMatchException;
import com.example.userauthenticationservice.exception.UserAlreadyExistException;
import com.example.userauthenticationservice.exception.UserNotRegisteredException;
import com.example.userauthenticationservice.models.User;

public interface IAuthService {
    User signUp(String email, String password) throws UserAlreadyExistException;

    User login(String email, String password) throws UserNotRegisteredException, PasswordMissMatchException;
}
