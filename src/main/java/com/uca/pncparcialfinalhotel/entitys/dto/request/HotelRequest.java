package com.uca.pncparcialfinalhotel.entitys.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRequest {

    @NotBlank(message = "Hotel name is required")
    private String name;

    @NotBlank(message = "Hotel address is required")
    private String address;

    @NotBlank(message = "Hotel city is required")
    private String city;

    @NotBlank(message = "Hotel phone is required")
    private String phone;

    private Integer rating;
}

