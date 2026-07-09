package com.uca.pncparcialfinalhotel.controller;

import com.uca.pncparcialfinalhotel.service.RoomService;
import com.uca.pncparcialfinalhotel.entitys.dto.request.RoomRequest;
import com.uca.pncparcialfinalhotel.entitys.dto.response.RoomResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(@Valid @RequestBody RoomRequest roomRequest) {
        RoomResponse createdRoom = roomService.createRoom(roomRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        List<RoomResponse> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<RoomResponse>> getRoomsByHotel(@PathVariable Long hotelId) {
        List<RoomResponse> rooms = roomService.getRoomsByHotelId(hotelId);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/hotel/{hotelId}/available")
    public ResponseEntity<List<RoomResponse>> getAvailableRoomsByHotel(@PathVariable Long hotelId) {
        List<RoomResponse> rooms = roomService.getAvailableRoomsByHotel(hotelId);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/hotel/{hotelId}/available-dates")
    public ResponseEntity<List<RoomResponse>> getAvailableRoomsByDateRange(
            @PathVariable Long hotelId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {
        List<RoomResponse> rooms = roomService.getAvailableRoomsByHotelAndDateRange(hotelId, checkIn, checkOut);
        return ResponseEntity.ok(rooms);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomRequest roomRequest) {
        try {
            RoomResponse updatedRoom = roomService.updateRoom(id, roomRequest);
            return ResponseEntity.ok(updatedRoom);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}

