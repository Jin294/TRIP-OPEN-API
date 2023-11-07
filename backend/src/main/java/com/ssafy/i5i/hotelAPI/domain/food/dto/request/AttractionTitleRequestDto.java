package com.ssafy.i5i.hotelAPI.domain.food.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
public class AttractionTitleRequestDto {
	private String attractionName;
	private Long distance;
	private String sorted;
}
