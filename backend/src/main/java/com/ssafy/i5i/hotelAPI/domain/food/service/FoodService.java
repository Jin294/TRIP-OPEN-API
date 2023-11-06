package com.ssafy.i5i.hotelAPI.domain.food.service;

import java.util.List;

import com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodResponseDto;
import com.ssafy.i5i.hotelAPI.domain.hotel.dto.request.AttractionCoordinateRequestDto;
import com.ssafy.i5i.hotelAPI.domain.hotel.dto.request.AttractionNameRequestDto;

public interface FoodService {
	List<FoodResponseDto> getFoodFromTravle(AttractionNameRequestDto attractionNameRequestDto);

	List<FoodResponseDto> getFoodFromLngLat(AttractionCoordinateRequestDto attractionCoordinateRequestDto);
}
