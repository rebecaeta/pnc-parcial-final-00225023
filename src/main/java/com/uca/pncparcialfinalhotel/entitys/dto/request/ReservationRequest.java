package com.uca.pncparcialfinalhotel.entitys.dto.request;

import com.uca.pncparcialfinalhotel.entitys.enums.ReservationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {

    @NotNull(message = "Check-in date is required")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    private LocalDate checkOutDate;

    @NotNull(message = "Number of guests is required")
    private Integer numberOfGuests;

    private ReservationStatus status;

    private String specialRequests;

    @NotNull(message = "Guest ID is required")
    private Long guestId;

    @NotNull(message = "Room ID is required")
    private Long roomId;
}

