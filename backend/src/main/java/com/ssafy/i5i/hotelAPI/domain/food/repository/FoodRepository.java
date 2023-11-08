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
	// @Query("select c from MemberChallenge c where c.member.idx = :memberIdx order by c.status asc,c.endTime asc")
	// List<MemberChallenge> findMemberChallengeMemberIdx(@Param("memberIdx") Long memberIdx);

	// @Query("SELECT c FROM Food c WHERE c.attraction.contentId = :attractionId")
	// Optional<List<FoodResponseDto>> findByAttractionId(@Param("attractionId") Integer attractionId);

	@Query("SELECT f FROM Food f WHERE f.foodLongitude >= :minX AND f.foodLongitude <= :maxX AND f.foodLatitude >= :minY AND f.foodLatitude <= :maxY")
	Optional<List<Food>> getAroundFoodList(double maxY, double maxX, double minY, double minX);


	@Query(value = "SELECT f.food_id as id, f.food_name as foodName, f.food_type as foodType, "
		+ "f.food_longitude as foodLongitude, f.food_latitude as foodLatitude, "
		+ "f.food_jjim as foodJjim, f.food_score as foodScore, "
		+ "f.food_star as foodStar, f.food_staruser as foodStarUser, "
		+ "ST_Distance_Sphere(POINT(:longitude, :latitude), POINT(food_longitude, food_latitude)) as distance "
		+ "From food as f "
		+ "HAVING distance <= :distance", nativeQuery = true)
	Optional<List<FoodResponseDtoInterface>> findByCoordinate(@Param("latitude") Double latitude, @Param("longitude") Double longitude,
		@Param("distance") Long distance);


	@Query(value = "SELECT f.food_id as id, f.food_name as foodName, f.food_type as foodType, "
		+ "f.food_longitude as foodLongitude, f.food_latitude as foodLatitude, "
		+ "f.food_jjim as foodJjim, f.food_score as foodScore, "
		+ "f.food_star as foodStar, f.food_staruser as foodStarUser, "
		+ "(6371*acos(cos(radians(:latitude))*cos(radians(food_latitude))*cos(radians(food_longitude) "
		+ "-radians(:longitude))+sin(radians(:latitude))*sin(radians(food_latitude)))) "
		+ "as distance "
		+ "From food as f "
		+ "HAVING distance <= :distance", nativeQuery = true)
	Optional<List<FoodResponseDtoInterface>> findByCoordinate3(@Param("latitude") Double latitude, @Param("longitude") Double longitude,
		@Param("distance") Long distance);


	// @Query("SELECT f, "
	// 	+ "(6371 * acos(cos(radians(:latitude)) * cos(radians(f.foodLatitude)) * cos(radians(f.foodLongitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(f.foodLatitude))) as distance) "
	// 	+ "FROM Food f WHERE "
	// 	+ "6371 * acos(cos(radians(:latitude)) * cos(radians(f.foodLatitude)) * cos(radians(f.foodLongitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(f.foodLatitude)) <= :distance)")
	// Optional<List<Food>> findFoodWithinDistance(@Param("latitude") Double latitude, @Param("longitude") Double longitude, @Param("distance") Long distance);


	@Query("SELECT f FROM Food f WHERE f.foodName = :name")
	Food findByFoodName(@Param("name") String name);


}