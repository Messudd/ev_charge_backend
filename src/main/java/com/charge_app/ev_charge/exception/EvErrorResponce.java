package com.charge_app.ev_charge.exception;

import lombok.Getter;

@Getter
public class EvErrorResponce {
    private  String message;

    public  EvErrorResponce(String message){
        this.message = message;
    }
}
