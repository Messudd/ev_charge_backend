package com.charge_app.ev_charge.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginRequest {
    private  String email;
    private  String password;
}
