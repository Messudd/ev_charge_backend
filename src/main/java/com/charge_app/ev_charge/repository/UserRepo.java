package com.charge_app.ev_charge.repository;
import com.charge_app.ev_charge.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    @Query("select u from User u where u.email = :email ")
    Optional<User> userCheckWithEmail(String email);
    @Query("select u from User u where u.usrName = :username ")
    Optional<User> userCheckWithUsername(String username);
    @Query("select u from User u where u.gender = :gender")
    List<User> getUserByFilterGender(String gender);
    @Query(value = "select u.profile_id from app.user as u where u.id = :id",nativeQuery = true)
    long getProfileIdFromUser(long id);
    @Query(value = "select u from app.user as u where u.email = :username and u.password = :password",nativeQuery = true)
    Optional<User> getUserWithUsernameAndPassword(String username , String password);
    @Query(value = "select count(id) from app.user as u where u.role_id = (select auth.id from app.authority as auth where auth.role = :role)",nativeQuery = true)
    long getUserCountByRole(String role);
    @Query(value = "select count(id) from app.user as u where u.gender = :gender",nativeQuery = true)
    long getUserCountByGender(String gender);
    @Query(value = "select u from app.user as u where u.profile_id = :id",nativeQuery = true)
    Optional<User> getUserByProfileId(long id);
    @Query(value = "select profile_id from app.user as u where u.id = :id",nativeQuery = true)
    Optional<Long> getProfileIdByUserId(long id);
}
