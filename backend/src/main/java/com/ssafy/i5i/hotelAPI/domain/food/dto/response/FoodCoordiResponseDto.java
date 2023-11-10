package com.ssafy.i5i.hotelAPI.domain.food.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
public class FoodCoordiResponseDto {
	private Long id;
	private String foodName;
	private String foodType;
	private Double foodLongitude;
	private Double foodLatitude;
	private Integer foodJjim;
	private Integer foodScore;
	private Double foodStar;
	private Integer foodStarUser;
	private Double Distance;

	// public FoodResponseDto(FoodResponseDtoInterface foodResponseDtoInterface) {
	// 	this.id = foodResponseDtoInterface.getId();
	// 	this.foodName = foodResponseDtoInterface.getFoodName();
	// 	this.foodType = foodResponseDtoInterface.getFoodType();
	// 	this.foodLongitude = foodResponseDtoInterface.getFoodLongitude();
	// 	this.foodLatitude = foodResponseDtoInterface.getFoodLatitude();
	// 	this.foodJjim = foodResponseDtoInterface.getFoodJjim();
	// 	this.foodScore = foodResponseDtoInterface.getFoodScore();
	// 	this.foodStar = foodResponseDtoInterface.getFoodStar();
	// 	this.foodStarUser = foodResponseDtoInterface.getFoodStarUser();
	// 	this.Distance = foodResponseDtoInterface.getDistance();
	// }
}
