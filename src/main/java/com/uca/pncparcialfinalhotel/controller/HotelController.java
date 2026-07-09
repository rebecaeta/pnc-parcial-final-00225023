package com.uca.pncparcialfinalhotel.controller;

import com.uca.pncparcialfinalhotel.service.HotelService;
import com.uca.pncparcialfinalhotel.entitys.dto.request.HotelRequest;
import com.uca.pncparcialfinalhotel.entitys.dto.response.HotelResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
@AllArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelResponse> createHotel(@Valid @RequestBody HotelRequest hotelRequest) {
        HotelResponse createdHotel = hotelService.createHotel(hotelRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHotel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<HotelResponse>> getAllHotels() {
        List<HotelResponse> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok(hotels);
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<HotelResponse> getHotelByName(@PathVariable String name) {
        return hotelService.getHotelByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-city/{city}")
    public ResponseEntity<List<HotelResponse>> getHotelsByCity(@PathVariable String city) {
        List<HotelResponse> hotels = hotelService.getHotelsByCity(city);
        return ResponseEntity.ok(hotels);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelResponse> updateHotel(@PathVariable Long id, @Valid @RequestBody HotelRequest hotelRequest) {
        try {
            HotelResponse updatedHotel = hotelService.updateHotel(id, hotelRequest);
            return ResponseEntity.ok(updatedHotel);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }
}

