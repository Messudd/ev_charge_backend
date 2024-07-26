package com.charge_app.ev_charge.service.impl;

import com.charge_app.ev_charge.dto.UserLoginRequest;
import com.charge_app.ev_charge.exception.UserException;
import com.charge_app.ev_charge.repository.RoleRepo;
import com.charge_app.ev_charge.repository.UserRepo;
import com.charge_app.ev_charge.user.Role;
import com.charge_app.ev_charge.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {
    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public  AuthService(UserRepo userRepo,RoleRepo roleRepo,PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // sign up user method with user-role. //
    // todo userNotFoundException email or username check and password encode for security -- implement userDetailService and D.injection - PasswordEncoder!

    @Transactional
    public User register(User user) {
        Optional<User> us = userRepo.userCheckWithEmail(user.getEmail());
        if(us.isPresent()){
            throw new UserException("This user is already registered | " + us.get().getEmail(),HttpStatus.BAD_REQUEST);
        }
        else{
            if (user.getProfile() != null || user.getStations() != null || user.getRole() != null) {
                throw new UserException("Just a request endpoint for user main variables, " +
                        "The object should not have stations , profile or role entries !", HttpStatus.BAD_REQUEST);
            }
            else {
                Optional<Role> rol = roleRepo.getRoleByRoleName("USER");
                String encodePassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodePassword);
                if(rol.isPresent()){
                    rol.get().addUser(user);
                    user.setRole(rol.get());
                }
                else{
                    Role roll = new Role("USER");
                    user.setRole(roll);
                    roll.addUser(user);
                }
                return userRepo.save(user);
            }
        }
    }
    public String userLogin(UserLoginRequest req) {
        Optional<User> us = userRepo.userCheckWithEmail(req.getEmail());
        if(us.isPresent()){
            String encodedPassword = us.get().getPassword();
            boolean confirmPass = passwordEncoder.matches(req.getPassword(),encodedPassword);
            if(confirmPass){
                return "User verified!";
            }
            throw new UserException("user credentials are not valid !",HttpStatus.BAD_REQUEST);
        }
        throw new UserException("No users found, you must register to log in.",HttpStatus.BAD_REQUEST);
    }
}
