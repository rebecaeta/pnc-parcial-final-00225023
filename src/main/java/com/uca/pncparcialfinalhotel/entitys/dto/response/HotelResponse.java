package com.uca.pncparcialfinalhotel.entitys.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponse {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String phone;
    private Integer rating;
}

