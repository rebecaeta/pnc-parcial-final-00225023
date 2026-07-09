package com.uca.pncparcialfinalhotel.controller;

import com.uca.pncparcialfinalhotel.service.ReservationService;
import com.uca.pncparcialfinalhotel.entitys.dto.request.ReservationRequest;
import com.uca.pncparcialfinalhotel.entitys.dto.response.ReservationResponse;
import com.uca.pncparcialfinalhotel.entitys.enums.ReservationStatus;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@AllArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody ReservationRequest reservationRequest) {
        try {
            ReservationResponse createdReservation = reservationService.createReservation(reservationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        List<ReservationResponse> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/guest/{guestId}")
    public ResponseEntity<List<ReservationResponse>> getReservationsByGuest(@PathVariable Long guestId) {
        List<ReservationResponse> reservations = reservationService.getReservationsByGuest(guestId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/guest/{guestId}/active")
    public ResponseEntity<List<ReservationResponse>> getActiveReservationsByGuest(@PathVariable Long guestId) {
        List<ReservationResponse> reservations = reservationService.getActiveReservationsByGuest(guestId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<ReservationResponse>> getReservationsByRoom(@PathVariable Long roomId) {
        List<ReservationResponse> reservations = reservationService.getReservationsByRoom(roomId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ReservationResponse>> getReservationsByStatus(@PathVariable ReservationStatus status) {
        List<ReservationResponse> reservations = reservationService.getReservationsByStatus(status);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/room/{roomId}/availability")
    public ResponseEntity<Boolean> checkRoomAvailability(
            @PathVariable Long roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {
        boolean available = reservationService.isRoomAvailable(roomId, checkIn, checkOut);
        return ResponseEntity.ok(available);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponse> updateReservation(
            @PathVariable Long id, @Valid @RequestBody ReservationRequest reservationRequest) {
        try {
            ReservationResponse updatedReservation = reservationService.updateReservation(id, reservationRequest);
            return ResponseEntity.ok(updatedReservation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<ReservationResponse> confirmReservation(@PathVariable Long id) {
        try {
            ReservationResponse confirmedReservation = reservationService.confirmReservation(id);
            return ResponseEntity.ok(confirmedReservation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<ReservationResponse> cancelReservation(@PathVariable Long id) {
        try {
            ReservationResponse cancelledReservation = reservationService.cancelReservation(id);
            return ResponseEntity.ok(cancelledReservation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}

