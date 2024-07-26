package com.charge_app.ev_charge.controller;

import com.charge_app.ev_charge.entity.Station;
import com.charge_app.ev_charge.service.impl.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ev/v1/user/station")
@CrossOrigin(origins = {"https://192.168.1.13:3000",
        "https://localhost:3000"},allowedHeaders = "*")
public class StationController {
    private StationService stationService;

    @Autowired
    public StationController(StationService stationService){
        this.stationService = stationService;
    }
    @GetMapping("/all/{userId}")
    public List<Station> getStationByUserId(@PathVariable long userId){
        return stationService.getAllUserStations(userId);
    }
    @PostMapping("/{userId}")
    public Station saveStation(@RequestBody Station station , @PathVariable long userId){
       return stationService.saveStationByUserId(station,userId);
    }
    @DeleteMapping("/{userId}/{stationId}")
    public Station deleteStationFromList(@PathVariable long userId,@PathVariable long stationId){
       return stationService.removeStationFromList(userId,stationId);
    }
}
