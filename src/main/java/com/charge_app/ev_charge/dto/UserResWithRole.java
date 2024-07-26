package com.charge_app.ev_charge.dto;

public record UserResWithRole(long id,String fullName,String userName,String password,
                              String email,String gender,RoleResponce roleResponce) {
}
