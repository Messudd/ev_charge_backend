package com.charge_app.ev_charge.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Entity
@Table (name = "charge_station" , schema = "ev")
public class EvStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  long id;
    @Column(name = "name")
    private String name;
    @Column( name = "city")
    private  String city;
    @Column(name = "town")
    private  String town;
    @Column(name = "street")
    private  String street;
    @Column(name = "neighbourhood")
    private  String neighbourhood;
    @Column(name = "image")
    private String image;
    @Column(name = "description")
    private  String description;
    @Column(name = "type")
    private  String type;
    @Column(name = "latitude")
    private  String latitude;
    @Column (name = "longitude")
    private  String longitude;
    @Column(name = "model")
    private  String model;
    @Column(name = "power")
    private  String power;
    @Column(name = "status")
    private  String status;
    @Column(name = "total_socket")
    private  int total;
    @Column(name = "model_url")
    private  String modelUrl;
}
