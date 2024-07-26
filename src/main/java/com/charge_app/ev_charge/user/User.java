package com.charge_app.ev_charge.user;
import com.charge_app.ev_charge.entity.Profile;
import com.charge_app.ev_charge.entity.Station;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user", schema = "app")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    @Column(name = "full_name", nullable = false)
    private  String fullName;
    @Column(name = "user_name",nullable = false)
    private  String usrName;
    @Column(name = "email",nullable = false , unique = true)
    private  String email;
    @Column(name = "password",nullable = false)
    private  String password;
    @Column(name = "gender")
    private String gender;
    // bi directional relation //
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "role_id")
    private  Role role;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    // unidirectional relation //
    @ManyToMany(cascade = {CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JoinTable(name = "user_station" ,schema = "app",
                joinColumns = {@JoinColumn(name = "user_id")},
                inverseJoinColumns = {@JoinColumn(name = "station_id")}
                )
    private List<Station> stations;

    public void addStation(Station station){
        if(stations == null){
            stations = new ArrayList<>();
        }
        stations.add(station);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> authoritys = new HashSet<>();
        authoritys.add(role);
        return authoritys;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}

