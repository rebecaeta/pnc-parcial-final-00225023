package com.uca.pncparcialfinalhotel.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.uca.pncparcialfinalhotel.entitys.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Room number is required")
    @Column(nullable = false)
    private String roomNumber;

    @NotNull(message = "Room type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType type;

    @NotNull(message = "Room price is required")
    @Positive(message = "Price must be positive")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerNight;

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean available = true;

    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be positive")
    @Column(nullable = false)
    private Integer capacity;

    @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();
}

