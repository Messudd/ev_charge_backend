package com.charge_app.ev_charge.repository;

import com.charge_app.ev_charge.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StationRepo extends JpaRepository<Station,Long>{
    @Query(value = "select s from app.station as s where s.id in (" +
            "select station_id from app.user_station as us where us.user_id = :userId)",nativeQuery = true)
    List<Station> getStationsByUserId(long userId);

    @Query(value = "select s from app.station as s where s.latitude = :lat and s.longitude = :lng",nativeQuery = true)
    Optional<Station> checkIsExistStation(String lat, String lng);
    @Query(value = "select user_id from app.user_station as s where s.station_id = :stationId",nativeQuery = true)
    List<Long> getUserIdListByStationId(long stationId);
    /*@Query(value = "delete from app.user_station as s where s.station_id = :stationId",nativeQuery = true)
    void removeDepFromJunctionTab(long stationId);*/
}
