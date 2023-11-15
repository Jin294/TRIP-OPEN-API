package com.ssafy.i5i.hotelAPI.domain.user.repository;

import com.ssafy.i5i.hotelAPI.domain.user.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Long> {
    @Query("SELECT e FROM Email e WHERE e.email = :email and e.isAuthorized = true")
    Optional<Email> selectAuthorizedEmail(@Param("email") String email);

    @Query("SELECT e FROM Email e WHERE e.email = :email and e.isAuthorized = false")
    Optional<Email> selectUnAuthorizedEmail(@Param("email") String email);

    @Modifying
    @Query("update Email e set e.createdTime =:createdTime where e.email_id =:emailId")
    int setCreatedTime(@Param("createdTime") LocalDateTime createdTime, @Param("userId") Long emailId);

    @Modifying
    @Query("update Email e set e.code =:code where e.email_id =:emailId")
    int setCode(@Param("code") Long code, @Param("userId") Long emailId);
}
