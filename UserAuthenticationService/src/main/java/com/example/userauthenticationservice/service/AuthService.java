package com.example.userauthenticationservice.service;

import com.example.userauthenticationservice.exception.PasswordMissMatchException;
import com.example.userauthenticationservice.exception.UserAlreadyExistException;
import com.example.userauthenticationservice.exception.UserNotRegisteredException;
import com.example.userauthenticationservice.models.Role;
import com.example.userauthenticationservice.models.User;
import com.example.userauthenticationservice.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public User login(String email, String password) throws UserNotRegisteredException, PasswordMissMatchException {

        Optional<User> userOptional = userRepo.findByEmail(email);
        if(userOptional.isEmpty()) {
            throw new UserNotRegisteredException("Please Signup First......!!!");
        }

//        If User is present then check for the storedPassword matches with the given password or not

        String storedPassword = userOptional.get().getPassword();
        if(!bCryptPasswordEncoder.matches(password, storedPassword)) {
            throw new PasswordMissMatchException("Please add correct password...!!!");
        }

        return userOptional.get();
    }
}
