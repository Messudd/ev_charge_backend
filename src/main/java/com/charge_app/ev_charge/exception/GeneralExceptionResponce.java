package com.charge_app.ev_charge.exception;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class GeneralExceptionResponce {
    private  String timeStamp;
    private String message;
    private HttpStatus status;
    public GeneralExceptionResponce(String timeStamp,String message,HttpStatus status){
        this.timeStamp = timeStamp;
        this.message = message;
        this.status = status;
    }
}
