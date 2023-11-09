package com.ssafy.i5i.hotelAPI.domain.food.service;

import java.util.List;

import com.ssafy.i5i.hotelAPI.domain.food.dto.request.AttractionCoordiRequestDto;
import com.ssafy.i5i.hotelAPI.domain.food.dto.request.AttractionTitleRequestDto;
import com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodResponseDto;
import com.ssafy.i5i.hotelAPI.domain.food.entity.Food;

public interface FoodService {
	List<FoodResponseDto> getFoodFromTravle(AttractionTitleRequestDto attractionTitleRequestDto);

	List<FoodResponseDto> getFoodFromLngLatv1(AttractionCoordiRequestDto attractionCoordiRequestDto);

	List<FoodResponseDto> getFoodFromLngLatv2(AttractionCoordiRequestDto attractionCoordiRequestDto);

	List<FoodResponseDto> getFoodFromLngLatv3(AttractionCoordiRequestDto attractionCoordiRequestDto);

	// List<FoodResponseDto> getFoodFromLngLatv5(AttractionCoordiRequestDto attractionCoordiRequestDto);

	Food findFoodByName(String name);

	Food findFoodByIdx(Long idx);
}
