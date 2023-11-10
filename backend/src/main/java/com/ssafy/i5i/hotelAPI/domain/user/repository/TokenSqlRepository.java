package com.ssafy.i5i.hotelAPI.domain.user.repository;

import com.ssafy.i5i.hotelAPI.domain.user.entity.TokenUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenSqlRepository extends JpaRepository<TokenUser, Long> {
    @Query("SELECT u FROM TokenUser u WHERE u.token = :token AND u.isDeleted = 0")
    Optional<TokenUser> findByToken(@Param("token") String token);

    @Modifying
    @Query("update TokenUser u set u.count=u.count+1 where u.token = :token")
    void updateCount(@Param("token") String token);
}
