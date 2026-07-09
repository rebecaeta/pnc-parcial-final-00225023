package com.uca.pncparcialfinalhotel.repository;

import com.uca.pncparcialfinalhotel.entitys.Reservation;
import com.uca.pncparcialfinalhotel.entitys.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByGuestId(Long guestId);
    List<Reservation> findByRoomId(Long roomId);
    List<Reservation> findByStatus(ReservationStatus status);

    @Query("SELECT r FROM Reservation r WHERE r.room.id = :roomId " +
           "AND r.status NOT IN ('CANCELLED') " +
           "AND (" +
           "  (:checkIn < r.checkOutDate AND :checkOut > r.checkInDate)" +
           ")")
    List<Reservation> findConflictingReservations(
            @Param("roomId") Long roomId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut);

    @Query("SELECT r FROM Reservation r WHERE r.guest.id = :guestId " +
           "AND r.checkInDate <= CURRENT_DATE " +
           "AND r.checkOutDate >= CURRENT_DATE")
    List<Reservation> findActiveReservationsByGuest(@Param("guestId") Long guestId);

}


