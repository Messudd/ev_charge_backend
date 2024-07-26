package com.charge_app.ev_charge.dto;

public record UserDataResponce(long id , String username, String email , String gender,
                               RoleResponce roleResponce,ProfileResponce profileResponce) {
}

