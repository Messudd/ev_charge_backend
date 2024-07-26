package com.charge_app.ev_charge.service.impl;

import com.charge_app.ev_charge.entity.EvStation;
import com.charge_app.ev_charge.exception.EvStationException;
import com.charge_app.ev_charge.repository.EvStationDao;
import com.charge_app.ev_charge.service.EvStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


// TODO  YOU MUST WRITE A VALİDATİON --- SERVICE LAYER
@Service
public class EvStationServiceImpl implements EvStationService {
    public EvStationDao evStationDao;
    @Autowired
    public EvStationServiceImpl(EvStationDao evStationDao){
        this.evStationDao = evStationDao;
    }
    @Override
    public List<EvStation> getAllCityStation(String city) {
        List<EvStation> evALLFromCity =  evStationDao.getAllCityStation(city);
        try {
            int number = Integer.valueOf(city);
            System.out.println("Path variable convert : " +  number);
            throw  new EvStationException("path variable : [ " + number + " ] " + " must be a String --- Please Enter a City Name !! ", HttpStatus.BAD_REQUEST);
        }
        catch (NumberFormatException ex){
            if (!evALLFromCity.isEmpty()) {
                return evALLFromCity;
            }
            throw new EvStationException("No stations found in this area", HttpStatus.BAD_REQUEST);
        }
    }
    @Override
    public List<EvStation> getAllTownFromCity(String city, String town) {
        List<EvStation> evALLFromTown =  evStationDao.getAllTownFromCity(city,town);
        if(!evALLFromTown.isEmpty()){
            return  evALLFromTown;
        }
        throw  new EvStationException("No stations found in this area", HttpStatus.BAD_REQUEST);
    }
    @Override
    public List<EvStation> getAllNeighbourhoodFromTown(String city, String town, String neighbourhood) {
        List<EvStation> evALLFromStreet =  evStationDao.getAllNeighbourhoodFromTown(city,town, neighbourhood);
        if(!evALLFromStreet.isEmpty()){
            return  evALLFromStreet;
        }
        throw  new EvStationException("No stations found in this area", HttpStatus.BAD_REQUEST);
    }
    @Override
    public List<EvStation> getAllStreetFromDb(String city, String town, String neighbourhood, String street) {
        List<EvStation> evALLFromNeighbourhood =  evStationDao.getAllStreetFromDb(city,town,neighbourhood,street);
        if(!evALLFromNeighbourhood.isEmpty()){
            return  evALLFromNeighbourhood;
        }
        throw  new EvStationException("No stations found in this area", HttpStatus.BAD_REQUEST);
    }
    @Override
    public EvStation getStationById(Long id) {
        Optional<EvStation> ev = evStationDao.findById(id);
        if(ev.isPresent()){
            return  ev.get();
        }
        throw  new EvStationException("No station with this id was found" + ":" +id, HttpStatus.BAD_REQUEST);
    }
    //TODO EV STATİON CHECK DATA AND CREATE EXCEPTİON
    @Override
    public EvStation saveStation(EvStation evStation) {
        if(evStation.getCity() == null || evStation.getCity().isEmpty() || evStation.getName() == null || evStation.getName().isEmpty() || evStation.getLatitude() == null
                || evStation.getLatitude().isEmpty() || evStation.getLongitude() == null || evStation.getLongitude().isEmpty() || evStation.getTown().isEmpty()
                || evStation.getTown() == null || evStation.getNeighbourhood().isEmpty()|| evStation.getNeighbourhood() == null
                || evStation.getStreet() == null || evStation.getStreet().isEmpty() || evStation.getPower().isEmpty()
                || evStation.getPower() == null || evStation.getType() == null){
            throw  new EvStationException("Station name , latitude , longitude ,city ,town , neighbourhood, street , power and type values can not be empty or null ! ",
                    HttpStatus.BAD_REQUEST);
        }
        Optional<EvStation> ev = evStationDao.searchStationFromDB(evStation.getLatitude(), evStation.getLongitude());
        if(ev.isPresent()){
            throw  new EvStationException("The Station is already registered - lat : " + ev.get().getLatitude() + " , long: " + ev.get().getLongitude() + ".",HttpStatus.FOUND);
        }
        else {
            evStationDao.saveStationDB(evStation);
            return evStationDao.searchStationFromDB(evStation.getLatitude(),evStation.getLongitude()).get();
        }
    }
    @Override
    public EvStation deleteStation(Long id) {
        EvStation delEv = getStationById(id);
        evStationDao.deleteStationFromDB(delEv.getId());
        return delEv;
    }
}
