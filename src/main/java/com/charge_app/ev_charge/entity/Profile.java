package com.charge_app.ev_charge.entity;
import com.charge_app.ev_charge.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "profile", schema = "app")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "image")
    private  String image;
    @Column(name = "note")
    private  String note;
    @JsonIgnore
    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
              CascadeType.REFRESH,CascadeType.PERSIST},mappedBy = "profile")
    private User user;

    public Profile(String image , String note){
        this.image = image;
        this.note = note;
    }
}
