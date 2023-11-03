package com.ssafy.i5i.hotelAPI.domain.user.repository;


import com.ssafy.i5i.hotelAPI.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.token = :token AND u.isDeleted = 0")
    Optional<User> findByToken(@Param("token") String token);

    Optional<User> findById(String id);
}
