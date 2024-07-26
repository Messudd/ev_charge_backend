package com.charge_app.ev_charge.repository;

import com.charge_app.ev_charge.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role,Long> {
    @Query(value = "select * from app.authority as r where r.role = :roleName",nativeQuery = true)
    Optional<Role> getRoleByRoleName(String roleName);
}
