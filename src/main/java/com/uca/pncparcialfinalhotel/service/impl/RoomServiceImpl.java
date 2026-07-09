package com.uca.pncparcialfinalhotel.service.impl;

import com.uca.pncparcialfinalhotel.entitys.Room;
import com.uca.pncparcialfinalhotel.entitys.Hotel;
import com.uca.pncparcialfinalhotel.entitys.dto.request.RoomRequest;
import com.uca.pncparcialfinalhotel.entitys.dto.response.RoomResponse;
import com.uca.pncparcialfinalhotel.repository.RoomRepository;
import com.uca.pncparcialfinalhotel.repository.HotelRepository;
import com.uca.pncparcialfinalhotel.service.RoomService;
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
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    @Override
    public RoomResponse createRoom(RoomRequest roomRequest) {
        Room room = new Room();
        mapRequestToEntity(roomRequest, room);
        Room savedRoom = roomRepository.save(room);
        return mapEntityToResponse(savedRoom);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoomResponse> getRoomById(Long id) {
        return roomRepository.findById(id).map(this::mapEntityToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RoomResponse updateRoom(Long id, RoomRequest roomRequest) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
        mapRequestToEntity(roomRequest, room);
        Room updatedRoom = roomRepository.save(room);
        return mapEntityToResponse(updatedRoom);
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomResponse> getRoomsByHotelId(Long hotelId) {
        return roomRepository.findByHotelIdAndAvailableTrue(hotelId).stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomResponse> getAvailableRoomsByHotel(Long hotelId) {
        return roomRepository.findByHotelIdAndAvailableTrue(hotelId).stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomResponse> getAvailableRoomsByHotelAndDateRange(Long hotelId, LocalDate checkIn, LocalDate checkOut) {
        return roomRepository.findAvailableRoomsByHotelAndDateRange(hotelId, checkIn, checkOut).stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    private RoomResponse mapEntityToResponse(Room room) {
        RoomResponse response = new RoomResponse();
        response.setId(room.getId());
        response.setRoomNumber(room.getRoomNumber());
        response.setType(room.getType());
        response.setPricePerNight(room.getPricePerNight());
        response.setAvailable(room.getAvailable());
        response.setCapacity(room.getCapacity());
        response.setDescription(room.getDescription());
        response.setHotelId(room.getHotel().getId());
        return response;
    }

    private void mapRequestToEntity(RoomRequest request, Room entity) {
        entity.setRoomNumber(request.getRoomNumber());
        entity.setType(request.getType());
        entity.setPricePerNight(request.getPricePerNight());
        entity.setAvailable(request.getAvailable() != null ? request.getAvailable() : true);
        entity.setCapacity(request.getCapacity());
        entity.setDescription(request.getDescription());
        
        if (request.getHotelId() != null) {
            Hotel hotel = hotelRepository.findById(request.getHotelId())
                    .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + request.getHotelId()));
            entity.setHotel(hotel);
        }
    }
}



