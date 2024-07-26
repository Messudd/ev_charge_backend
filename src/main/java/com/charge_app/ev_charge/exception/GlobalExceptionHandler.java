package com.charge_app.ev_charge.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<GeneralExceptionResponce> userExceptionHandler(UserException userException) {
        GeneralExceptionResponce exResponce = new GeneralExceptionResponce(new Timestamp(System.currentTimeMillis()).toString(),
                userException.getMessage(), userException.getStatus());
        return new ResponseEntity<>(exResponce, userException.getStatus());
    }
    @ExceptionHandler
    public ResponseEntity<EvErrorResponce> evStationHandler(EvStationException ex){
        EvErrorResponce evErrorResponce = new EvErrorResponce(ex.getMessage());
        return  new ResponseEntity<>(evErrorResponce,ex.getStatus());
    }
    @ExceptionHandler
    private ResponseEntity<GeneralExceptionResponce> exceptionHandler(Exception ex) {
        GeneralExceptionResponce exResponce = new GeneralExceptionResponce(new Timestamp(System.currentTimeMillis()).toString(),
                ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(exResponce, HttpStatus.BAD_REQUEST);
    }
}
