package com.charge_app.ev_charge.repository;

import com.charge_app.ev_charge.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfileRepo extends JpaRepository<Profile,Long> {

}
