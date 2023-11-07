package com.ssafy.i5i.hotelAPI.domain.food.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AttractionCoordiRequestDto {
	private Double latitude;
	private Double longitude;
	private Long distance;
	private String sorted;
}
