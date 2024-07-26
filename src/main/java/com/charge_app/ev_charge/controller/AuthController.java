package com.charge_app.ev_charge.controller;

import com.charge_app.ev_charge.dto.UserLoginRequest;
import com.charge_app.ev_charge.dto.UserResWithRole;
import com.charge_app.ev_charge.service.impl.AuthService;
import com.charge_app.ev_charge.user.User;
import com.charge_app.ev_charge.util.GenericDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/ev/v1/auth")
@CrossOrigin(origins = {"https://192.168.1.13:3000",
        "https://localhost:3000"},allowedHeaders = "*")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public UserResWithRole createUserWithRole(@RequestBody User user){
        User userRes = authService.register(user);
        return GenericDtoConverter.userWithRoleConvertFromUser(user);
    }

    @PostMapping("/login")
    public String userLogin(@RequestBody UserLoginRequest userLoginRequest){
       return authService.userLogin(userLoginRequest);
    }
}
