package com.ssafy.i5i.hotelAPI.domain.food.service;

import java.util.List;

import com.ssafy.i5i.hotelAPI.domain.food.dto.request.AttractionCoordiRequestDto;
import com.ssafy.i5i.hotelAPI.domain.food.dto.request.AttractionTitleRequestDto;
import com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodCoordiResponseDto;
import com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodResponseDto;
import com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodTitleResponseDto;

public interface FoodService {
	List<FoodResponseDto.TitleD> getFoodFromTravle(AttractionTitleRequestDto attractionTitleRequestDto);

	List<FoodCoordiResponseDto> getFoodFromLngLatv(AttractionCoordiRequestDto attractionCoordiRequestDto);

	/**
	 *
	 */
	//
	// List<FoodResponseDto> getFoodFromLngLatv1(AttractionCoordiRequestDto attractionCoordiRequestDto);
	//
	// List<FoodResponseDto> getFoodFromLngLatv3(AttractionCoordiRequestDto attractionCoordiRequestDto);
	//
	// Food findFoodByName(String name);
	//
	// Food findFoodByIdx(Long idx);
}
