package com.ssafy.i5i.hotelAPI.domain.docs.repository;

import com.ssafy.i5i.hotelAPI.domain.docs.entity.ApiType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApiTypeRepository extends JpaRepository<ApiType,Long> {
    List <ApiType> findAll();
}
