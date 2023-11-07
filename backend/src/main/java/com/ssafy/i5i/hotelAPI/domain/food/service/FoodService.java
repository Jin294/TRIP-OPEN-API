package com.ssafy.i5i.hotelAPI.domain.food.service;

import java.util.List;

import com.ssafy.i5i.hotelAPI.domain.food.dto.request.AttractionTitleRequestDto;
import com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodResponseDto;
import com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodResponseDtoInterface;
import com.ssafy.i5i.hotelAPI.domain.hotel.dto.request.AttractionCoordinateRequestDto;


public interface FoodService {
	List<FoodResponseDto> getFoodFromTravle(AttractionTitleRequestDto attractionTitleRequestDto);

	List<FoodResponseDto> getFoodFromLngLat(AttractionCoordinateRequestDto attractionCoordinateRequestDto);
}
