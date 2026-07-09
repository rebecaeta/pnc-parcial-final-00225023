package com.uca.pncparcialfinalhotel.service.impl;

import com.uca.pncparcialfinalhotel.entitys.Hotel;
import com.uca.pncparcialfinalhotel.entitys.dto.request.HotelRequest;
import com.uca.pncparcialfinalhotel.entitys.dto.response.HotelResponse;
import com.uca.pncparcialfinalhotel.repository.HotelRepository;
import com.uca.pncparcialfinalhotel.service.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    @Override
    public HotelResponse createHotel(HotelRequest hotelRequest) {
        Hotel hotel = new Hotel();
        mapRequestToEntity(hotelRequest, hotel);
        Hotel savedHotel = hotelRepository.save(hotel);
        return mapEntityToResponse(savedHotel);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HotelResponse> getHotelById(Long id) {
        return hotelRepository.findById(id).map(this::mapEntityToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotelResponse> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public HotelResponse updateHotel(Long id, HotelRequest hotelRequest) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
        mapRequestToEntity(hotelRequest, hotel);
        Hotel updatedHotel = hotelRepository.save(hotel);
        return mapEntityToResponse(updatedHotel);
    }

    @Override
    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HotelResponse> getHotelByName(String name) {
        return hotelRepository.findByName(name).map(this::mapEntityToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotelResponse> getHotelsByCity(String city) {
        return hotelRepository.findByCity(city)
                .stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    private HotelResponse mapEntityToResponse(Hotel hotel) {
        HotelResponse response = new HotelResponse();
        response.setId(hotel.getId());
        response.setName(hotel.getName());
        response.setAddress(hotel.getAddress());
        response.setCity(hotel.getCity());
        response.setPhone(hotel.getPhone());
        response.setRating(hotel.getRating());
        return response;
    }

    private void mapRequestToEntity(HotelRequest request, Hotel entity) {
        entity.setName(request.getName());
        entity.setAddress(request.getAddress());
        entity.setCity(request.getCity());
        entity.setPhone(request.getPhone());
        entity.setRating(request.getRating());
    }
}



