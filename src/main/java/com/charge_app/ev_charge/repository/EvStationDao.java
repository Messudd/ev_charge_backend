package com.charge_app.ev_charge.repository;

import com.charge_app.ev_charge.entity.EvStation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EvStationDao extends JpaRepository<EvStation,Long> {
    @Query(value = "SELECT e.id,e.name,e.city,e.town,e.street,e.neighbourhood,e.image,e.description,"+
            "e.type,e.latitude,e.longitude,e.power,e.status,e.total_socket,e.model_url,e.model FROM ev.charge_station as e WHERE e.city = :city ",nativeQuery = true)
    List<EvStation> getAllCityStation(String city);
    @Query(value = "SELECT e.id,e.name,e.city,e.town,e.street,e.neighbourhood,e.image,e.description," +
            "e.type,e.latitude,e.longitude,e.power,e.status,e.total_socket,e.model_url,e.model FROM ev.charge_station as e WHERE e.city = :city and e.town = :town",nativeQuery = true)
    List<EvStation> getAllTownFromCity(String city, String town);
    @Query(value = "SELECT e.id,e.name,e.city,e.town,e.street,e.neighbourhood,e.image,e.description," +
            "e.type,e.latitude,e.longitude,e.power,e.status,e.total_socket,e.model_url,e.model FROM ev.charge_station as e WHERE e.city = :city and e.town = :town and e.neighbourhood ilike (%:neighbourhood%)",nativeQuery = true)
    List<EvStation> getAllNeighbourhoodFromTown(String city, String town, String neighbourhood );
    @Query(value = "SELECT e.id,e.name,e.city,e.town,e.street,e.neighbourhood,e.image,e.description," +
            "e.type,e.latitude,e.longitude,e.power,e.status,e.total_socket,e.model_url,e.model FROM ev.charge_station as e WHERE e.city = :city and e.town = :town and e.neighbourhood ilike (%:neighbourhood% ) and e.street ilike (%:street%) ",nativeQuery = true)
    List<EvStation> getAllStreetFromDb(String city, String town, String neighbourhood, String street);
    @Query(value = "SELECT e.id,e.name,e.city,e.town,e.street,e.neighbourhood,e.image,e.description,"+
            "e.type,e.latitude,e.longitude,e.power,e.status,e.total_socket,e.model_url,e.model FROM ev.charge_station as e WHERE e.latitude = :latitude and e.longitude = :longitude ",nativeQuery = true)
    Optional<EvStation> searchStationFromDB(String latitude , String longitude);
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO ev.charge_station ( name,city,town,street,neighbourhood,image,description,type,latitude,longitude,power,status,total_socket,model_url,model )" +
            " VALUES" + "( " +
            ":#{#ev.name}, " +
            ":#{#ev.city}, " +
            ":#{#ev.town}, " +
            ":#{#ev.street}, " +
            ":#{#ev.neighbourhood}, " +
            ":#{#ev.image}, " +
            ":#{#ev.description}, " +
            ":#{#ev.type}, " +
            ":#{#ev.latitude}, " +
            ":#{#ev.longitude}, " +
            ":#{#ev.power}, " +
            ":#{#ev.status}, " +
            ":#{#ev.total}, " +
            ":#{#ev.modelUrl}, " +
            ":#{#ev.model} " +
            " )",nativeQuery = true)
    void saveStationDB(@Param("ev") EvStation ev);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ev.charge_station as e WHERE e.id = :id",nativeQuery = true)
    void deleteStationFromDB(Long id);
    @Query(value = "SELECT e.id,e.name,e.city,e.town,e.street,e.neighbourhood,e.image,e.description," +
            "e.type,e.latitude,e.longitude,e.power,e.status,e.total_socket,e.model_url,e.model FROM ev.charge_station as e WHERE e.id = :id",nativeQuery = true)
    Optional<EvStation> findById(Long id);
}
