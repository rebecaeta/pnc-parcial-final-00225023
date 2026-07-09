package com.uca.pncparcialfinalhotel.entitys.dto.response;

import com.uca.pncparcialfinalhotel.entitys.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {
    private Long id;
    private String roomNumber;
    private RoomType type;
    private BigDecimal pricePerNight;
    private Boolean available;
    private Integer capacity;
    private String description;
    private Long hotelId;
}

