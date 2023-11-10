package com.ssafy.i5i.hotelAPI.domain.food.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
public class FoodTitleResponseDto {
	private Integer attractionId;
	private String attracionName;
	private Double attractionLongitude;
	private Double attractionLatitude;
	private Long foodId;
	private String foodName;
	private String foodType;
	private Double foodLongitude;
	private Double foodLatitude;
	private Integer foodJjim;
	private Integer foodScore;
	private Double foodStar;
	private Integer foodStarUser;

	public FoodResponseDto.TitleD convertToFDto (){
		return new FoodResponseDto.TitleD(this.attractionId, this.attracionName,
			this.attractionLongitude,this.attractionLatitude,
			this.foodId,this.foodName,this.foodType,
			this.foodLongitude, this.foodLatitude, this.foodJjim, this.foodScore, this.foodStar,
			this.foodStarUser, null);
	}
}
