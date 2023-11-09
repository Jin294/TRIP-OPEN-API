package com.ssafy.i5i.hotelAPI.domain.food.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
public class FoodTitleResponseDto {
	private Integer AttractionId;
	private String AttracionName;
	private Double AttractionLongitude;
	private Double AttractionLatitude;
	private Long foodId;
	private String foodName;
	private String foodType;
	private Double foodLongitude;
	private Double foodLatitude;
	private Integer foodJjim;
	private Integer foodScore;
	private Double foodStar;
	private Integer foodStarUser;
	private Double Distance;
}
