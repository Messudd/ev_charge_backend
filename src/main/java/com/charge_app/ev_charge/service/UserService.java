package com.charge_app.ev_charge.service;
import com.charge_app.ev_charge.user.User;
import java.util.List;


public interface UserService {
    User getUserById(long id);
    User getUserByEmail(String email);
    List<User> getAllUser();
    List<User> getUserByFilterGender(String gender);
    User deleteUser(long id);
    User userRoleChange(String role, long id);
    long getUserCountByRole(String role);
    long getUserCountByGender(String gender);
    long getProfileIdByUserId(long id);
}
