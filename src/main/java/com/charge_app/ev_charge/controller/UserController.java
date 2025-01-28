package com.charge_app.ev_charge.controller;
import com.charge_app.ev_charge.dto.UserDataResponce;
import com.charge_app.ev_charge.dto.UserResponceUnDetail;
import com.charge_app.ev_charge.service.UserService;
import com.charge_app.ev_charge.user.User;
import com.charge_app.ev_charge.util.GenericDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/ev/v1/user")
@CrossOrigin(origins = {"http://192.168.1.37:3000",
        "http://localhost:3000"},allowedHeaders = "*")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDataResponce getUserById(@PathVariable long id){
        User user = userService.getUserById(id);
        return GenericDtoConverter.userConvert(user);
    }
    @GetMapping("/byEmail/{email}")
    public UserDataResponce getUserByEmail(@PathVariable String email){
        User user = userService.getUserByEmail(email);
        return GenericDtoConverter.userConvert(user);
    }
    @GetMapping("/profileId/{userId}")
    public long getProfileIdByUserId(@PathVariable long userId){
       return userService.getProfileIdByUserId(userId);
    }
}
