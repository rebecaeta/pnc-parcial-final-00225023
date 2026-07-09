package com.uca.pncparcialfinalhotel.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import com.uca.pncparcialfinalhotel.entitys.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Check-in date is required")
    @Column(nullable = false)
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    @Column(nullable = false)
    private LocalDate checkOutDate;

    @NotNull(message = "Number of guests is required")
    @Column(nullable = false)
    private Integer numberOfGuests;

    @NotNull(message = "Reservation status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status = ReservationStatus.PENDING;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Column(length = 500)
    private String specialRequests;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id", nullable = false)
    private User guest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @PrePersist
    @PreUpdate
    private void calculateTotalPrice() {
        if (totalPrice == null && room != null && checkInDate != null && checkOutDate != null) {
            long nights = java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate);
            totalPrice = room.getPricePerNight().multiply(BigDecimal.valueOf(Math.max(1, nights)));
        }
    }
}

