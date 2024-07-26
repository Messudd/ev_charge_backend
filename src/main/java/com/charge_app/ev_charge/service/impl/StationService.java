package com.charge_app.ev_charge.service.impl;

import com.charge_app.ev_charge.entity.Station;
import com.charge_app.ev_charge.exception.UserException;
import com.charge_app.ev_charge.repository.StationRepo;
import com.charge_app.ev_charge.repository.UserRepo;
import com.charge_app.ev_charge.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StationService {

    private StationRepo stationRepo;
    private UserRepo userRepo;

    @Autowired
    public StationService(StationRepo stationRepo, UserRepo userRepo){
        this.stationRepo = stationRepo;
        this.userRepo = userRepo;
    }
    public List<Station> getStationsByUserId(long id){
        return stationRepo.getStationsByUserId(id);
    }
    @Transactional
    public Station removeStationFromList(long userId, long stationId){
        Optional<Station> st = stationRepo.findById(stationId);
        Optional<User> us = userRepo.findById(userId);
        if(st.isPresent() && us.isPresent()){
                List<Station> stations = us.get().getStations();
                if(stations.isEmpty() || stations == null){
                    throw new UserException("User's station list was not found !",HttpStatus.BAD_REQUEST);
                }
                List<Long> list = stationRepo.getUserIdListByStationId(stationId);
                List<Station> newList = new ArrayList<>();
                if(list.size() > 1){
                    for (Station stn : stations){
                        if(stn.getId() != stationId){
                            newList.add(stn);
                        }
                    }
                    us.get().setStations(newList);
                    userRepo.save(us.get());
                    return stationRepo.findById(stationId).orElseThrow(() ->
                            new RuntimeException("No station with this id found : "+ stationId));
                }
                //stationRepo.removeDepFromJunctionTab(stationId);
                stationRepo.delete(st.get());
                return  st.get();
        }
        throw new UserException("Id not found !"+ stationId +" : " +userId ,HttpStatus.NOT_FOUND);
    }

    public List<Station> getAllUserStations(long id){
        Optional<User> user = userRepo.findById(id);
        if(user.isPresent()){
            return  user.get().getStations();
        }
        throw new UserException("User was not found !",HttpStatus.NOT_FOUND);
    }

    @Transactional
    public Station checkInStationList(Station station ,User user){
        List<Station> stsList = stationRepo.findAll();
        for (Station s : stsList) {
            if (s.getLatitude().equals(station.getLatitude()) && s.getLongitude().equals(station.getLongitude())
                    && s.getStationName().equals(station.getStationName()) && s.getCity().equals(station.getCity())
                    && s.getTown().equals(station.getTown()) && s.getDistrict().equals(station.getDistrict()))  {
                    user.addStation(s);
                    return stationRepo.save(s);
            }
        }
        user.addStation(station);
        return stationRepo.save(station);
    }

    public Station saveStationByUserId(Station station, long id) {
        Optional<User> us = userRepo.findById(id);
        if (us.isPresent()) {
            if (us.get().getStations() == null || us.get().getStations().isEmpty()) {
                return checkInStationList(station,us.get());
            }
            else {
                for (Station s : us.get().getStations()) {
                    if (s.getLatitude().equals(station.getLatitude()) && s.getLongitude().equals(station.getLongitude())) {
                        throw new UserException("This station already exist at your list", HttpStatus.BAD_REQUEST);
                    }
                }
              return checkInStationList(station,us.get());
            }
        }
        throw  new UserException("User was not found !", HttpStatus.NOT_FOUND);
    }
}
