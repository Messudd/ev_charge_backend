package com.charge_app.ev_charge.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "station" ,schema = "app")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "station_name")
    private String stationName;
    @Column(name = "latitude")
    private String latitude;
    @Column(name = "longitude")
    private String longitude;
    @Column(name = "city")
    private String city;
    @Column(name = "town")
    private String town;
    @Column(name = "district")
    private String district;
    @Column(name = "street")
    private String street;
    @Column(name = "power")
    private String power;
    @Column(name = "type")
    private String type;
    @Column(name = "address")
    private String address;
    @Column(name = "image_url")
    private String imageUrl;

}
