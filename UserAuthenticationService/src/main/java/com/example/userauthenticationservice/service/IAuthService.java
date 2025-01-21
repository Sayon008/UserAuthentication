package com.example.userauthenticationservice.service;

import com.example.userauthenticationservice.exception.PasswordMissMatchException;
import com.example.userauthenticationservice.exception.UserAlreadyExistException;
import com.example.userauthenticationservice.exception.UserNotRegisteredException;
import com.example.userauthenticationservice.models.User;
import org.antlr.v4.runtime.misc.Pair;

public interface IAuthService {
    User signUp(String email, String password) throws UserAlreadyExistException;

    Pair<User,String> login(String email, String password) throws UserNotRegisteredException, PasswordMissMatchException;

    Boolean validateToken(String token, Long userId);
}
