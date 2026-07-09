package com.uca.pncparcialfinalhotel.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Hotel name is required")
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank(message = "Hotel address is required")
    @Column(nullable = false)
    private String address;

    @NotBlank(message = "Hotel city is required")
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "Hotel phone is required")
    private String phone;

    @Column(columnDefinition = "INTEGER DEFAULT 5")
    private Integer rating;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> staff = new ArrayList<>();
}

