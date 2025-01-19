package com.example.userauthenticationservice.controller;

import com.example.userauthenticationservice.dtos.LoginRequest;
import com.example.userauthenticationservice.dtos.SignupRequest;
import com.example.userauthenticationservice.dtos.UserDTO;
import com.example.userauthenticationservice.exception.PasswordMissMatchException;
import com.example.userauthenticationservice.exception.UserAlreadyExistException;
import com.example.userauthenticationservice.exception.UserNotRegisteredException;
import com.example.userauthenticationservice.models.User;
import com.example.userauthenticationservice.repository.UserRepo;
import com.example.userauthenticationservice.service.IAuthService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody SignupRequest signupRequest) throws UserAlreadyExistException {
        try{
            User user = authService.signUp(signupRequest.getEmail(),signupRequest.getPassword());

            return new ResponseEntity<>(from(user), HttpStatus.CREATED);

        }catch(UserAlreadyExistException exception){
            throw exception;
        }


    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequest loginRequest) throws UserNotRegisteredException, PasswordMissMatchException {
        try{
//            Now after implementing the JWT instead of returning User we will return the Pair<User,Token> together
//            User user = authService.login(loginRequest.getEmail(),loginRequest.getPassword());

            Pair<User,String> pairResponse = authService.login(loginRequest.getEmail(),loginRequest.getPassword());

//            Now if we want to refer to the variables of the Pair class -> either User or the token
//            For User - pairResponse.a
//            For Token - pairResponce.b

//            Idially we sent token in our Cookies and cookies are added as a part of headers

//            Add Headers -
            MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
//            Setting the token with HTTPHeaders using cookie
//            We have added the SET_COOKIE because to give reference to my front-end that this token should be stored in your cookie
            headers.add(HttpHeaders.SET_COOKIE,pairResponse.b);

            return new ResponseEntity<>(from(pairResponse.a),headers, HttpStatus.OK);

        }catch(UserNotRegisteredException exception){
            throw exception;
        }catch(PasswordMissMatchException exception){
            throw exception;
        }
    }

//    Mapper toUserDTO from User
    public UserDTO from(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoles(user.getRoles());

        return userDTO;
    }
}
