package com.uca.pncparcialfinalhotel.service.impl;

import com.uca.pncparcialfinalhotel.entitys.Reservation;
import com.uca.pncparcialfinalhotel.entitys.enums.ReservationStatus;
import com.uca.pncparcialfinalhotel.entitys.User;
import com.uca.pncparcialfinalhotel.entitys.Room;
import com.uca.pncparcialfinalhotel.entitys.dto.request.ReservationRequest;
import com.uca.pncparcialfinalhotel.entitys.dto.response.ReservationResponse;
import com.uca.pncparcialfinalhotel.repository.ReservationRepository;
import com.uca.pncparcialfinalhotel.repository.UserRepository;
import com.uca.pncparcialfinalhotel.repository.RoomRepository;
import com.uca.pncparcialfinalhotel.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Override
    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        // Validate room availability
        if (!isRoomAvailable(reservationRequest.getRoomId(), reservationRequest.getCheckInDate(),
                reservationRequest.getCheckOutDate())) {
            throw new RuntimeException("Room is not available for the selected dates");
        }

        Reservation reservation = new Reservation();
        mapRequestToEntity(reservationRequest, reservation);
        reservation.setStatus(ReservationStatus.PENDING);
        Reservation savedReservation = reservationRepository.save(reservation);
        return mapEntityToResponse(savedReservation);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReservationResponse> getReservationById(Long id) {
        return reservationRepository.findById(id).map(this::mapEntityToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationResponse updateReservation(Long id, ReservationRequest reservationRequest) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));

        // Check if status is not already checked out or cancelled
        if (reservation.getStatus() == ReservationStatus.CHECKED_OUT ||
            reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new RuntimeException("Cannot update a " + reservation.getStatus() + " reservation");
        }

        mapRequestToEntity(reservationRequest, reservation);
        Reservation updatedReservation = reservationRepository.save(reservation);
        return mapEntityToResponse(updatedReservation);
    }

    @Override
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
        reservationRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> getReservationsByGuest(Long guestId) {
        return reservationRepository.findByGuestId(guestId).stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> getReservationsByRoom(Long roomId) {
        return reservationRepository.findByRoomId(roomId).stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> getReservationsByStatus(ReservationStatus status) {
        return reservationRepository.findByStatus(status).stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> getActiveReservationsByGuest(Long guestId) {
        return reservationRepository.findActiveReservationsByGuest(guestId).stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationResponse cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));

        if (reservation.getStatus() == ReservationStatus.CHECKED_OUT) {
            throw new RuntimeException("Cannot cancel a checked-out reservation");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        Reservation updatedReservation = reservationRepository.save(reservation);
        return mapEntityToResponse(updatedReservation);
    }

    @Override
    public ReservationResponse confirmReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));

        if (reservation.getStatus() != ReservationStatus.PENDING) {
            throw new RuntimeException("Only pending reservations can be confirmed");
        }

        reservation.setStatus(ReservationStatus.CONFIRMED);
        Reservation updatedReservation = reservationRepository.save(reservation);
        return mapEntityToResponse(updatedReservation);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isRoomAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        List<Reservation> conflictingReservations =
                reservationRepository.findConflictingReservations(roomId, checkIn, checkOut);
        return conflictingReservations.isEmpty();
    }

    private ReservationResponse mapEntityToResponse(Reservation reservation) {
        ReservationResponse response = new ReservationResponse();
        response.setId(reservation.getId());
        response.setCheckInDate(reservation.getCheckInDate());
        response.setCheckOutDate(reservation.getCheckOutDate());
        response.setNumberOfGuests(reservation.getNumberOfGuests());
        response.setStatus(reservation.getStatus());
        response.setTotalPrice(reservation.getTotalPrice());
        response.setSpecialRequests(reservation.getSpecialRequests());
        response.setGuestId(reservation.getGuest().getId());
        response.setRoomId(reservation.getRoom().getId());
        return response;
    }

    private void mapRequestToEntity(ReservationRequest request, Reservation entity) {
        entity.setCheckInDate(request.getCheckInDate());
        entity.setCheckOutDate(request.getCheckOutDate());
        entity.setNumberOfGuests(request.getNumberOfGuests());
        entity.setSpecialRequests(request.getSpecialRequests());

        User guest = userRepository.findById(request.getGuestId())
                .orElseThrow(() -> new RuntimeException("Guest not found with id: " + request.getGuestId()));
        entity.setGuest(guest);

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + request.getRoomId()));
        entity.setRoom(room);
    }
}



