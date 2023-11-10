package com.ssafy.i5i.hotelAPI.domain.hotel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.i5i.hotelAPI.domain.hotel.entity.Attraction;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {
	Optional<Attraction> findByTitle (String title);
}