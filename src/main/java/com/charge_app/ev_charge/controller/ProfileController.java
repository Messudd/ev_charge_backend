package com.charge_app.ev_charge.controller;

import com.charge_app.ev_charge.dto.ProfileReq;
import com.charge_app.ev_charge.dto.ProfileResponce;
import com.charge_app.ev_charge.dto.ProfileWithUser;
import com.charge_app.ev_charge.entity.Profile;
import com.charge_app.ev_charge.service.ProfileService;
import com.charge_app.ev_charge.service.UserService;
import com.charge_app.ev_charge.user.User;
import com.charge_app.ev_charge.util.GenericDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/ev/v1/profile")
@CrossOrigin(origins = {"https://192.168.1.13:3000",
        "https://localhost:3000"},allowedHeaders = "*")
public class ProfileController {

    private ProfileService profileService;
    private UserService userService;

    @Autowired
    public ProfileController(ProfileService profileService, UserService userService){
        this.profileService = profileService;
        this.userService = userService;
    }
    // User profile match and add by using userId parameter.

    @GetMapping("/{profileId}")
    public ProfileResponce getProfileById(@PathVariable long profileId){
       Profile profile = profileService.getUserProfile(profileId);
       return new ProfileResponce(profileId,profile.getImage(),profile.getNote());
    }
    @PostMapping("/match/{userId}")
    public ProfileWithUser profileSaveWithUserId(@RequestBody Profile profile, @PathVariable long userId){
        User user = userService.getUserById(userId);
        Profile profileUser = profileService.profileMatchWithUser(profile,user);
       return GenericDtoConverter.userWithProfileConvert(profileUser);
    }
    @PostMapping("/{userId}")
    public ProfileResponce saveUserprofile(@RequestBody ProfileReq profileReq,@PathVariable long userId){
       Profile profile =  profileService.saveUserprofile(profileReq,userId);
       return  new ProfileResponce(profile.getId(),profile.getImage(),profile.getNote());
    }
    @PostMapping("/")
    public ProfileResponce saveJustProfile(@RequestBody Profile profile){
        Profile pro =  profileService.updateJustProfile(profile);
        return  new ProfileResponce(pro.getId(),pro.getImage(),pro.getNote());
    }
}
