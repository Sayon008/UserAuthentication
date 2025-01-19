package com.example.userauthenticationservice.service;

import com.example.userauthenticationservice.exception.PasswordMissMatchException;
import com.example.userauthenticationservice.exception.UserAlreadyExistException;
import com.example.userauthenticationservice.exception.UserNotRegisteredException;
import com.example.userauthenticationservice.models.Role;
import com.example.userauthenticationservice.models.User;
import com.example.userauthenticationservice.repository.UserRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User signUp(String email, String password) throws UserAlreadyExistException {
//        First we will check if the user initially exist or not in the dB

        Optional<User> userOptional = userRepo.findByEmail(email);
        if(userOptional.isPresent()) {
            throw new UserAlreadyExistException("Please Try Logging......!!!!");
        }

//        Else create a new User
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setCreated_At(new Date());
        user.setLastUpdated_At(new Date());

        Role role = new Role();
        role.setRoleValue("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);

        userRepo.save(user);

        return user;
    }

    @Override
    public Pair<User,String> login(String email, String password) throws UserNotRegisteredException, PasswordMissMatchException {

        Optional<User> userOptional = userRepo.findByEmail(email);
        if(userOptional.isEmpty()) {
            throw new UserNotRegisteredException("Please Signup First......!!!");
        }

//        If User is present then check for the storedPassword matches with the given password or not

        String storedPassword = userOptional.get().getPassword();
        if(!bCryptPasswordEncoder.matches(password, storedPassword)) {
            throw new PasswordMissMatchException("Please add correct password...!!!");
        }


        //Generating JWT

        //Payload Data  --> Hardcoded
//        String message = "{\n" +
//                "   \"email\": \"sayon@gmail.com\",\n" +
//                "   \"roles\": [\n" +
//                "      \"student\",\n" +
//                "      \"buddy\"\n" +
//                "   ],\n" +
//                "   \"expirationDate\": \"2ndApril2025\"\n" +
//                "}";

//        Coverting the message into byte array
//        byte[] content = message.getBytes(StandardCharsets.UTF_8);
//        Generating the JWT token
//        String token = Jwts.builder().content(content).compact();

//        Payload data from the User data(DB), Data Structure will be used to store the data -> Hashmap
        HashMap<String,Object> payload = new HashMap<>();
        Long nowInMillies = System.currentTimeMillis();
        payload.put("iat",nowInMillies);
        payload.put("exp",nowInMillies + 100000);
        payload.put("userId", userOptional.get().getId());
        payload.put("iss","Scaler");
        payload.put("roles", userOptional.get().getRoles());

//Payload is also known as claims

//        For generating signature we need secret key and Encryption Algo(HS256)
//        Most Popular library for generating secretkey and algo - MacAlgorithm

        MacAlgorithm algorithm = Jwts.SIG.HS256;
//        Generating the secret key using the MacAlgorithm library
        SecretKey secretKey = algorithm.key().build();

//        Passing the secret key along the token
        String token = Jwts.builder().claims(payload).signWith(secretKey).compact();

//        Our Algorithm has to work with the encoded headers and encoded Payload, SecretKey

//        Now want to return the user and the token both -> We can use Pain class to return both at the same time


//        return userOptional.get();
        return new Pair<User,String>(userOptional.get(),token);
    }


}
