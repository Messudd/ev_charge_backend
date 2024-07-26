package com.charge_app.ev_charge.controller;

import com.charge_app.ev_charge.dto.UserDataResponce;
import com.charge_app.ev_charge.dto.UserResWithRole;
import com.charge_app.ev_charge.service.UserService;
import com.charge_app.ev_charge.user.User;
import com.charge_app.ev_charge.util.GenericDtoConverter;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/ev/v1/users")
public class AdminController {

    private UserService userService;

    public  AdminController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/filter")
    public List<UserResWithRole> getUserByFilterGender(@RequestParam String gender){
        List<UserResWithRole> userResList = new ArrayList<>();
        List<User> users = userService.getUserByFilterGender(gender.toUpperCase());
        for(User u : users){
            userResList.add(GenericDtoConverter.userWithRoleConvertFromUser(u));
        }
        return userResList;
    }
    @GetMapping("/all")
    public List<UserResWithRole> getAllUser(){
        List<UserResWithRole> userResponceList = new ArrayList<>();
        List<User> getUsers = userService.getAllUser();
        for(User user : getUsers){
            userResponceList.add(GenericDtoConverter.userWithRoleConvertFromUser(user));
        }
        return userResponceList;
    }
    @GetMapping("/count")
    public long getUserCountByRole(@RequestParam String role){
        return userService.getUserCountByRole(role);
    }

    @GetMapping("/genderCount")
    public long getUserCountByGender(@RequestParam String gender){
        return userService.getUserCountByGender(gender);
    }
    @PostMapping("/{userId}/updateRole")
    public UserResWithRole changeRoleByUserId(@RequestParam String role, @PathVariable long userId){
        User user = userService.userRoleChange(role,userId);
        return GenericDtoConverter.userWithRoleConvertFromUser(user);
    }
    @DeleteMapping("/delete/{id}")
    public UserDataResponce removeUserById(@PathVariable long id){
        User user = userService.deleteUser(id);
        return GenericDtoConverter.userConvert(user);
    }
}
