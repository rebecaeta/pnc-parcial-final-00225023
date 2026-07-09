package com.uca.pncparcialfinalhotel.service;

import com.uca.pncparcialfinalhotel.entitys.dto.request.RoomRequest;
import com.uca.pncparcialfinalhotel.entitys.dto.response.RoomResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomService {
    RoomResponse createRoom(RoomRequest roomRequest);
    Optional<RoomResponse> getRoomById(Long id);
    List<RoomResponse> getAllRooms();
    RoomResponse updateRoom(Long id, RoomRequest roomRequest);
    void deleteRoom(Long id);
    List<RoomResponse> getRoomsByHotelId(Long hotelId);
    List<RoomResponse> getAvailableRoomsByHotel(Long hotelId);
    List<RoomResponse> getAvailableRoomsByHotelAndDateRange(Long hotelId, LocalDate checkIn, LocalDate checkOut);
}



