package com.ssafy.i5i.hotelAPI.domain.docs.repository;

import com.ssafy.i5i.hotelAPI.domain.docs.entity.ApiData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApiDataRepository extends JpaRepository<ApiData, Long> {
    List <ApiData> findAll();

    @Query("SELECT a" +
            "FROM ApiData a" +
            "JOIN FETCH a.variable")
    Optional<List<ApiData>> getApiDataById(@Param("apiDataId") Long apiId);
}
