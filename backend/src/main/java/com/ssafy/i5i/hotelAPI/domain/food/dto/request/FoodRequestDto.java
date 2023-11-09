package com.ssafy.i5i.hotelAPI.domain.food.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FoodRequestDto {
	private Double latitude;
	private Double longitude;
	private Long distance;
	private String sorted;
}
