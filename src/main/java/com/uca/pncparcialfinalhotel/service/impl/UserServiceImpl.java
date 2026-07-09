package com.uca.pncparcialfinalhotel.service.impl;

import com.uca.pncparcialfinalhotel.entitys.User;
import com.uca.pncparcialfinalhotel.entitys.Hotel;
import com.uca.pncparcialfinalhotel.entitys.enums.UserRole;
import com.uca.pncparcialfinalhotel.entitys.dto.request.UserRequest;
import com.uca.pncparcialfinalhotel.entitys.dto.response.UserResponse;
import com.uca.pncparcialfinalhotel.repository.UserRepository;
import com.uca.pncparcialfinalhotel.repository.HotelRepository;
import com.uca.pncparcialfinalhotel.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = new User();
        mapRequestToEntity(userRequest, user);
        User savedUser = userRepository.save(user);
        return mapEntityToResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponse> getUserById(Long id) {
        return userRepository.findById(id).map(this::mapEntityToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        mapRequestToEntity(userRequest, user);
        User updatedUser = userRepository.save(user);
        return mapEntityToResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponse> getUserByUsername(String username) {
        return userRepository.findByUsername(username).map(this::mapEntityToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponse> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(this::mapEntityToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role).stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getUsersByHotel(Long hotelId) {
        return userRepository.findByHotelId(hotelId).stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    private UserResponse mapEntityToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());
        if (user.getHotel() != null) {
            response.setHotelId(user.getHotel().getId());
        }
        return response;
    }

    private void mapRequestToEntity(UserRequest request, User entity) {
        entity.setUsername(request.getUsername());
        // encode password before storing
        if (request.getPassword() != null) {
            entity.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        entity.setEmail(request.getEmail());
        entity.setFullName(request.getFullName());
        entity.setPhone(request.getPhone());
        entity.setRole(request.getRole());
        
        if (request.getHotelId() != null) {
            Hotel hotel = hotelRepository.findById(request.getHotelId())
                    .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + request.getHotelId()));
            entity.setHotel(hotel);
        }
    }
}



