package com.uca.pncparcialfinalhotel.repository;

import com.uca.pncparcialfinalhotel.entitys.User;
import com.uca.pncparcialfinalhotel.entitys.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByRole(UserRole role);
    List<User> findByHotelId(Long hotelId);
}


