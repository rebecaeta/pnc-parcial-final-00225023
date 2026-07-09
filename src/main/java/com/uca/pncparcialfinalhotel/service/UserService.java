package com.uca.pncparcialfinalhotel.service;

import com.uca.pncparcialfinalhotel.entitys.dto.request.UserRequest;
import com.uca.pncparcialfinalhotel.entitys.dto.response.UserResponse;
import com.uca.pncparcialfinalhotel.entitys.enums.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    Optional<UserResponse> getUserById(Long id);
    List<UserResponse> getAllUsers();
    UserResponse updateUser(Long id, UserRequest userRequest);
    void deleteUser(Long id);
    Optional<UserResponse> getUserByUsername(String username);
    Optional<UserResponse> getUserByEmail(String email);
    List<UserResponse> getUsersByRole(UserRole role);
    List<UserResponse> getUsersByHotel(Long hotelId);
}



