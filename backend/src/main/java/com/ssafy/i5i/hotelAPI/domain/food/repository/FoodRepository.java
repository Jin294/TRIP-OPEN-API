package com.ssafy.i5i.hotelAPI.domain.food.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodResponseDto;
import com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodTitleResponseDto;
import com.ssafy.i5i.hotelAPI.domain.food.entity.Food;
import com.ssafy.i5i.hotelAPI.domain.food.entity.FoodResponseDtoInterface;

public interface FoodRepository extends JpaRepository<Food, Long> {

	@Query("SELECT f FROM Food f WHERE f.restaurantLongitude >= :minX AND f.restaurantLongitude <= :maxX AND f.restaurantLatitude >= :minY AND f.restaurantLatitude <= :maxY")
	Optional<List<Food>> getFoodFromLngLatv(@Param("maxY") double maxY, @Param("maxX") double maxX, @Param("minY") double minY, @Param("minX") double minX);

	@Query("SELECT new com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodTitleResponseDto(a.contentId, a.title, a.longitude, a.latitude, " +
		"f.id, f.restaurantName, f.restaurantType, f.restaurantLongitude, f.restaurantLatitude, " +
		"f.restaurantLike, f.restaurantScore, f.restaurantStar, f.restaurantStarUser) " +
		"FROM AttractionFood af " +
		"LEFT JOIN Attraction a ON af.attraction.contentId = a.contentId " +
		"LEFT JOIN Food f ON f.id = af.food.id " +
		"WHERE a.title = :title")
	Optional<List<FoodTitleResponseDto>> getFoodFromTravle(@Param("title") String title);

	@Query(value = "SELECT f.food_id as id, f.food_name as restaurantName, f.food_type as restaurantType, "
		+ "f.food_longitude as restaurantLongitude, f.food_latitude as restaurantLatitude, "
		+ "f.food_jjim as restaurantLike, f.food_score as restaurantScore, "
		+ "f.food_star as restaurantStar, f.food_staruser as restaurantStarUser, "
		+ "ST_Distance_Sphere(POINT(:longitude, :latitude), POINT(food_longitude, food_latitude)) as Distance "
		+ "From food as f "
		+ "HAVING distance <= :distance", nativeQuery = true)

	Optional<List<FoodResponseDtoInterface>> findByCoordinatev1(@Param("latitude") Double latitude, @Param("longitude") Double longitude,
		@Param("distance") Double distance);


	@Query(value = "SELECT f.food_id as id, f.food_name as restaurantName, f.food_type as restaurantType, "
		+ "f.food_longitude as restaurantLongitude, f.food_latitude as restaurantLatitude, "
		+ "f.food_jjim as restaurantLike, f.food_score as restaurantScore, "
		+ "f.food_star as restaurantStar, f.food_staruser as restaurantStarUser, "
		+ "(6371*acos(cos(radians(:latitude))*cos(radians(food_latitude))*cos(radians(food_longitude) "
		+ "-radians(:longitude))+sin(radians(:latitude))*sin(radians(food_latitude)))) "
		+ "as Distance "
		+ "From food as f "
		+ "HAVING distance <= :distance", nativeQuery = true)
	Optional<List<FoodResponseDtoInterface>> findByCoordinatev2(@Param("latitude") Double latitude, @Param("longitude") Double longitude,
		@Param("distance") Double distance);


}