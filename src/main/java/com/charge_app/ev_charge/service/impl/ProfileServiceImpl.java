package com.charge_app.ev_charge.service.impl;

import com.charge_app.ev_charge.dto.ProfileReq;
import com.charge_app.ev_charge.entity.Profile;
import com.charge_app.ev_charge.exception.UserException;
import com.charge_app.ev_charge.repository.ProfileRepo;
import com.charge_app.ev_charge.repository.UserRepo;
import com.charge_app.ev_charge.service.ProfileService;
import com.charge_app.ev_charge.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {
    private ProfileRepo profileRepo;
    private UserRepo userRepo;

    @Autowired
    public ProfileServiceImpl(ProfileRepo profileRepo, UserRepo userRepo) {
        this.profileRepo = profileRepo;
        this.userRepo = userRepo;
    }
    @Override
    public Profile getUserProfile(long id) {
        Optional<Profile> profile = profileRepo.findById(id);
        if (profile.isPresent()) {
            return profile.get();
        }
        throw new UserException("No profile found with this id : " + id, HttpStatus.NOT_FOUND);
    }
    @Override
    @Transactional
    public Profile profileMatchWithUser(Profile profile, User user) {
        if (user.getProfile() == null) {
            if (profile.getId() == 0) {
                profile.setUser(user);
                user.setProfile(profile);
                return profileRepo.save(profile);
            }
            else throw new UserException("The user does not already have a profile, the profile id value is unnecessary to add !", HttpStatus.BAD_REQUEST);
        }
        else {
            if (profile.getId() != 0) {
                if (userRepo.getProfileIdFromUser(user.getId()) == profile.getId()) {
                    profile.setUser(user);
                    user.setProfile(profile);
                    return profileRepo.save(profile);
                } else
                    throw new UserException("User already has a different profile, provide correct ID to update !", HttpStatus.BAD_REQUEST);
            }
            throw new UserException("User already has a profile. Another profile cannot be added to a user !", HttpStatus.BAD_REQUEST);
        }
    }
    @Override
    @Transactional
    public Profile saveUserprofile(ProfileReq profileReq , long userId) {
        Optional<User> user = userRepo.findById(userId);
        if(user.isPresent()){
            if((user.get().getProfile() == null)){ // so null
                Profile pro = new Profile(profileReq.getImage(),profileReq.getNote());
                user.get().setProfile(pro);
                pro.setUser(user.get());
                userRepo.save(user.get());
                return  pro;
            }
            else{
              Profile profile = profileRepo.findById(user.get().getProfile().getId()).get();
              profile.setImage(profileReq.getImage());
              profile.setNote(profileReq.getNote());
              user.get().setProfile(profile);
              profile.setUser(user.get());
              return profileRepo.save(profile);}
          }
            throw new UserException("user was not found for match !",HttpStatus.BAD_REQUEST);
        }

    @Override
    @Transactional
    public Profile updateJustProfile(Profile profile) {
        if(profile.getId() != 0){
            return  profileRepo.save(profile);
        }
        throw new RuntimeException("Necessary profile ID for the request !");
    }
}

