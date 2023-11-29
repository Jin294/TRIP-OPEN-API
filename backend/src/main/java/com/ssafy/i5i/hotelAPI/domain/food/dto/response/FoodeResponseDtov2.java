package com.ssafy.i5i.hotelAPI.domain.food.dto.response;

import com.ssafy.i5i.hotelAPI.domain.food.entity.FoodResponseDtoInterface;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
public class FoodeResponseDtov2{
	private Long id;
	private String restaurantName;
	private String restaurantType;
	private Double restaurantLongitude;
	private Double restaurantLatitude;
	private Integer restaurantLike;
	private Integer restaurantScore;
	private Double restaurantStar;
	private Integer restaurantStarUser;
	private Double Distance;

	public FoodeResponseDtov2(FoodResponseDtoInterface foodResponseDtoInterface) {
		this.id = foodResponseDtoInterface.getId();
		this.restaurantName = foodResponseDtoInterface.getRestaurantName();
		this.restaurantType = foodResponseDtoInterface.getRestaurantType();
		this.restaurantLongitude = foodResponseDtoInterface.getRestaurantLongitude();
		this.restaurantLatitude = foodResponseDtoInterface.getRestaurantLatitude();
		this.restaurantLike = foodResponseDtoInterface.getRestaurantLike();
		this.restaurantScore = foodResponseDtoInterface.getRestaurantScore();
		this.restaurantStar = foodResponseDtoInterface.getRestaurantStar();
		this.restaurantStarUser = foodResponseDtoInterface.getRestaurantStarUser();
		this.Distance = foodResponseDtoInterface.getDistance();
	}
}

