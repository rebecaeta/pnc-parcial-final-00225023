package com.uca.pncparcialfinalhotel.entitys.dto.request;

import com.uca.pncparcialfinalhotel.entitys.enums.RoomType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest {
    
    @NotNull(message = "Room number is required")
    private String roomNumber;
    
    @NotNull(message = "Room type is required")
    private RoomType type;
    
    @NotNull(message = "Room price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal pricePerNight;
    
    private Boolean available;
    
    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be positive")
    private Integer capacity;
    
    private String description;
    
    @NotNull(message = "Hotel ID is required")
    private Long hotelId;
}

