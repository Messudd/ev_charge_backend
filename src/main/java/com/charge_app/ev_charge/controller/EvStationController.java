package com.charge_app.ev_charge.controller;

import com.charge_app.ev_charge.entity.EvStation;
import com.charge_app.ev_charge.service.EvStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/geolocation/ev")
@CrossOrigin(origins = {"http://192.168.1.37:3000",
        "http://localhost:3000"},allowedHeaders = "*")

public class EvStationController {
    public EvStationService evStationService;
    @Autowired
    public EvStationController(EvStationService evStationService) {
        this.evStationService = evStationService;
    }

    @GetMapping("/{id}")
    public EvStation getEvStationById(@PathVariable Long id){
        return  evStationService.getStationById(id);
    }
    @GetMapping("/city/{city}")
    public List<EvStation> getAllEvFromCity(@PathVariable String city){
        return  evStationService.getAllCityStation(city);
    }
    @GetMapping("/town/{city}/{town}")
    public List<EvStation> getAllEvFromTown(@PathVariable String city , @PathVariable String town){
        return   evStationService.getAllTownFromCity(city,town);
    }
    @GetMapping("/neigh/{city}/{town}/{neighbourhood}")
    public List<EvStation> getAllEvFromTown(@PathVariable String city , @PathVariable String town , @PathVariable String neighbourhood){
        return  evStationService.getAllNeighbourhoodFromTown(city,town, neighbourhood);
    }
    @GetMapping("/street/{city}/{town}/{neighbourhood}/{street}")
    public List<EvStation> getAllEvFromTown(@PathVariable String city , @PathVariable String town , @PathVariable String neighbourhood , @PathVariable String street) {
        return evStationService.getAllStreetFromDb(city, town,  neighbourhood, street);
    }

    @PostMapping("/")
    public EvStation saveStation(@RequestBody EvStation evStation){
        return evStationService.saveStation(evStation);
    }

    @DeleteMapping("/{id}")
    public EvStation deleteStation(@PathVariable Long id){
        return  evStationService.deleteStation(id);
    }
}

