package com.uca.pncparcialfinalhotel.entitys.dto.response;

import com.uca.pncparcialfinalhotel.entitys.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer numberOfGuests;
    private ReservationStatus status;
    private BigDecimal totalPrice;
    private String specialRequests;
    private Long guestId;
    private Long roomId;
}

