package com.ssafy.i5i.hotelAPI.domain.food.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodTitleResponseDto;
import com.ssafy.i5i.hotelAPI.domain.food.entity.Food;


public interface FoodRepository extends JpaRepository<Food, Long> {

	@Query("SELECT f FROM Food f WHERE f.foodLongitude >= :minX AND f.foodLongitude <= :maxX AND f.foodLatitude >= :minY AND f.foodLatitude <= :maxY")
	Optional<List<Food>> getFoodFromLngLatv(double maxY, double maxX, double minY, double minX);

	@Query("SELECT new com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodTitleResponseDto(a.contentId, a.title, a.longitude, a.latitude, " +
		"f.id, f.foodName, f.foodType, f.foodLongitude, f.foodLatitude, " +
		"f.foodJjim, f.foodScore, f.foodStar, f.foodStarUser) " +
		"FROM AttractionFood af " +
		"LEFT JOIN Attraction a ON af.attraction.contentId = a.contentId " +
		"LEFT JOIN Food f ON f.id = af.food.id " +
		"WHERE a.title = :title")
	Optional<List<FoodTitleResponseDto>> getFoodFromTravle(@Param("title") String title);
}