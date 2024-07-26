package com.charge_app.ev_charge.service.impl;
import com.charge_app.ev_charge.exception.RoleException;
import com.charge_app.ev_charge.exception.UserException;
import com.charge_app.ev_charge.repository.RoleRepo;
import com.charge_app.ev_charge.repository.UserRepo;
import com.charge_app.ev_charge.service.UserService;
import com.charge_app.ev_charge.user.Role;
import com.charge_app.ev_charge.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepo userRepo;
    private RoleRepo roleRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }
    // orElseThrow for optional best practice ! //
    @Override
    public User getUserById(long id) {
        return userRepo.findById(id).orElseThrow(() ->
               new UserException("No user found with this id : " + id + " !",
                       HttpStatus.NOT_FOUND));
    }
    @Override
    public User getUserByEmail(String email) {
        return userRepo.userCheckWithEmail(email).orElseThrow(() ->
            new UserException("User was not found !",HttpStatus.BAD_REQUEST)
        );
    }
    @Override
    public List<User> getAllUser() {
        return userRepo.findAll();
    }
    @Override
    public List<User> getUserByFilterGender(String gender) {
        List<User> users = userRepo.getUserByFilterGender(gender);
        if (users.isEmpty()) {
            throw new UserException("No users were found matching the search criteria.",
                    HttpStatus.NOT_FOUND);
        }
        return users;
    }
    @Override
    public User deleteUser(long id) {
        User user = getUserById(id);
        userRepo.deleteById(id);
        return user;
    }
    // role  - change //
    @Override
    public User userRoleChange(String role, long id) {
        User user = getUserById(id);
        if (!(role.equalsIgnoreCase("ADMIN") || role.equalsIgnoreCase("USER"))) {
            throw new RoleException("The defined role value is invalid - Valid roles : [ 'USER' , 'ADMIN' ]",
                    HttpStatus.BAD_REQUEST);
        }
        else {
                if (!(user.getRole().getRole().equalsIgnoreCase(role))) {
                    Optional<Role> rol = roleRepo.getRoleByRoleName(role.toUpperCase());
                    if(rol.isPresent()){
                        user.setRole(rol.get());
                        rol.get().addUser(user);
                    }
                    else {
                        Role roll = new Role(role.toUpperCase());
                        user.setRole(roll);
                        roll.addUser(user);
                    }
                    return userRepo.save(user);
                }
                else throw new UserException("The value of the user belonging to this id is already : " +
                        role.toUpperCase() + " ---> To update, you can view the user's role name by making a request to " +
                        "the /user/{userId} api endpoint.!",HttpStatus.BAD_REQUEST);
        }
    }
    @Override
    public long getUserCountByRole(String role) {
        if (!(role.equalsIgnoreCase("ADMIN") || role.equalsIgnoreCase("USER"))) {
            throw new RoleException("The defined role value is invalid - Valid roles : [ 'USER' , 'ADMIN' ]",
                    HttpStatus.BAD_REQUEST);
        } else return userRepo.getUserCountByRole(role.toUpperCase());
    }
    @Override
    public long getUserCountByGender(String gender) {
        if (!(gender.equalsIgnoreCase("FEMALE") || gender.equalsIgnoreCase("MALE"))) {
            throw new RoleException("The defined gender value is invalid - Valid roles : [ 'FEMALE' , 'MALE' ]",
                    HttpStatus.BAD_REQUEST);
        } else return userRepo.getUserCountByGender(gender.toUpperCase());
    }

    @Override
    public long getProfileIdByUserId(long id) {
       return (userRepo.getProfileIdByUserId(id).isPresent()) ? userRepo.getProfileIdByUserId(id).get() : 0;
    }
    @Override // login process
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.userCheckWithEmail(email).orElseThrow(() ->
            new UsernameNotFoundException("user credentials are not valid.")
        );
    }
}
