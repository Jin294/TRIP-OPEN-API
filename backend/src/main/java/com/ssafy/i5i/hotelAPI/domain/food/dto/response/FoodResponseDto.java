package com.ssafy.i5i.hotelAPI.domain.food.dto.response;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
public class FoodResponseDto {
	private Long id;
	private String foodName;
	private String foodType;
	private BigDecimal foodLongtitude;
	private BigDecimal foodLatitude;
	private Integer foodJjim;
	private Integer foodScore;
	private Double foodStar;
	private Integer foodStarUser;
	private BigDecimal relativeDistance;
}
