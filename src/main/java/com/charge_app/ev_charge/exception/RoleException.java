package com.charge_app.ev_charge.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RoleException extends RuntimeException {
    private HttpStatus status;
    public RoleException(String message, HttpStatus status ){
        super(message);
        this.status = status;
    }
}
