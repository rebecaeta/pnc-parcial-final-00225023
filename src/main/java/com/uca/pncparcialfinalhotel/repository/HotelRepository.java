package com.uca.pncparcialfinalhotel.repository;

import com.uca.pncparcialfinalhotel.entitys.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByName(String name);
    Optional<Hotel> findByCity(String city);
}


