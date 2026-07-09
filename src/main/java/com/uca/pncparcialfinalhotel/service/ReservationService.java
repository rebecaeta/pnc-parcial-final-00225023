package com.uca.pncparcialfinalhotel.service;

import com.uca.pncparcialfinalhotel.entitys.dto.request.ReservationRequest;
import com.uca.pncparcialfinalhotel.entitys.dto.response.ReservationResponse;
import com.uca.pncparcialfinalhotel.entitys.enums.ReservationStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationService {
    ReservationResponse createReservation(ReservationRequest reservationRequest);
    Optional<ReservationResponse> getReservationById(Long id);
    List<ReservationResponse> getAllReservations();
    ReservationResponse updateReservation(Long id, ReservationRequest reservationRequest);
    void deleteReservation(Long id);
    List<ReservationResponse> getReservationsByGuest(Long guestId);
    List<ReservationResponse> getReservationsByRoom(Long roomId);
    List<ReservationResponse> getReservationsByStatus(ReservationStatus status);
    List<ReservationResponse> getActiveReservationsByGuest(Long guestId);
    ReservationResponse cancelReservation(Long id);
    ReservationResponse confirmReservation(Long id);
    boolean isRoomAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut);
}



