package com.charge_app.ev_charge.service;
import com.charge_app.ev_charge.dto.ProfileReq;
import com.charge_app.ev_charge.entity.Profile;
import com.charge_app.ev_charge.user.User;


public interface ProfileService {
    Profile getUserProfile(long id);
    Profile profileMatchWithUser(Profile profile, User user);
    Profile saveUserprofile(ProfileReq profileReq, long userId);
    Profile updateJustProfile(Profile profile);
}
