package com.uca.pncparcialfinalhotel.entitys.dto.response;

import com.uca.pncparcialfinalhotel.entitys.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String phone;
    private UserRole role;
    private Long hotelId;
}

