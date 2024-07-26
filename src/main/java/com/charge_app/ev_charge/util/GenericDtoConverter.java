package com.charge_app.ev_charge.util;

import com.charge_app.ev_charge.dto.*;
import com.charge_app.ev_charge.entity.Profile;
import com.charge_app.ev_charge.user.User;
public class GenericDtoConverter {
    public static UserDataResponce userConvert(User user){
        if(user.getRole() != null && user.getProfile() != null){
            return  new UserDataResponce(user.getId(),user.getUsrName(),user.getEmail(),user.getGender(),
                    new RoleResponce(user.getRole().getId(),user.getRole().getRole()),
                    new ProfileResponce(user.getProfile().getId(),user.getProfile().getImage(),user.getProfile().getNote()));
        }
       if(user.getProfile() != null){
            return  new UserDataResponce(user.getId(),user.getUsrName(),user.getEmail(),user.getGender(),
                    new RoleResponce(0,null),
                    new ProfileResponce(user.getProfile().getId(),user.getProfile().getImage(),user.getProfile().getNote()));
        }
       if(user.getRole() != null){
            return  new UserDataResponce(user.getId(),user.getUsrName(),user.getEmail(),user.getGender(),
                    new RoleResponce(user.getRole().getId(),user.getRole().getRole()),
                    new ProfileResponce(0,null,null));
        }
       return null;
    }
    public static UserResponceUnDetail userResUnDetailConvert(User user){
        return new  UserResponceUnDetail(user.getId(),user.getFullName(),
                    user.getUsrName(),user.getPassword(),user.getEmail(),user.getGender());
    }
    public static ProfileWithUser userWithProfileConvert(Profile profile){
        return new ProfileWithUser(profile.getUser().getId(),profile.getUser().getUsrName(),
                                   profile.getUser().getEmail(), profile.getUser().getGender(),
                                   new ProfileResponce(profile.getId(),profile.getImage(),profile.getNote()));
    }
    public static UserResWithRole  userWithRoleConvertFromUser(User user){
        return new UserResWithRole(user.getId(),user.getFullName(),user.getUsrName(),user.getPassword(),user.getEmail(),user.getGender(),
                new RoleResponce(user.getRole().getId(),user.getRole().getRole()));
    }
}
