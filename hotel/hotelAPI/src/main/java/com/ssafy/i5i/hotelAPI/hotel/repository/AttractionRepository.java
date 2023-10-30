package com.ssafy.i5i.hotelAPI.hotel.repository;

import com.ssafy.i5i.hotelAPI.hotel.entity.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    Attraction findByTitle (String title);
}
