package com.uca.pncparcialfinalhotel.repository;

import com.uca.pncparcialfinalhotel.entitys.Room;
import com.uca.pncparcialfinalhotel.entitys.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByHotelIdAndAvailableTrue(Long hotelId);

    @Query("SELECT r FROM Room r WHERE r.hotel.id = :hotelId " +
           "AND r.available = true " +
           "AND r.id NOT IN (" +
           "  SELECT res.room.id FROM Reservation res " +
           "  WHERE res.room.hotel.id = :hotelId " +
           "  AND res.status NOT IN ('CANCELLED') " +
           "  AND (" +
           "    (:checkIn < res.checkOutDate AND :checkOut > res.checkInDate)" +
           "  )" +
           ")")
    List<Room> findAvailableRoomsByHotelAndDateRange(
            @Param("hotelId") Long hotelId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut);
}


