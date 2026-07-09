package com.uca.pncparcialfinalhotel.service;

import com.uca.pncparcialfinalhotel.entitys.dto.request.HotelRequest;
import com.uca.pncparcialfinalhotel.entitys.dto.response.HotelResponse;

import java.util.List;
import java.util.Optional;

public interface HotelService {
    HotelResponse createHotel(HotelRequest hotelRequest);
    Optional<HotelResponse> getHotelById(Long id);
    List<HotelResponse> getAllHotels();
    HotelResponse updateHotel(Long id, HotelRequest hotelRequest);
    void deleteHotel(Long id);
    Optional<HotelResponse> getHotelByName(String name);
    List<HotelResponse> getHotelsByCity(String city);
}



