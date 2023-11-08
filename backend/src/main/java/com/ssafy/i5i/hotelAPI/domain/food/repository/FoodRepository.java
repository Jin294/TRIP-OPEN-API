package com.ssafy.i5i.hotelAPI.domain.food.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodResponseDto;
import com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodResponseDtoInterface;
import com.ssafy.i5i.hotelAPI.domain.food.entity.Food;


public interface FoodRepository extends JpaRepository<Food, Long> {
	@Query("SELECT c FROM Food c WHERE c.attraction.contentId = :attractionId")
	Optional<List<FoodResponseDto>> findByAttractionId(@Param("attractionId") Integer attractionId);


	@Query(value = "SELECT *, ST_Distance_Sphere(POINT(:longitude, :latitude), POINT(f.longitude, f.latitude)) as distance "
		+ "From food f "
		+ "HAVING distance <= :distance", nativeQuery = true)
	Optional<List<FoodResponseDtoInterface>> findByCoordinate(@Param("latitude") Double latitude, @Param("longitude") Double longitude,
		@Param("distance") Long distance);
}