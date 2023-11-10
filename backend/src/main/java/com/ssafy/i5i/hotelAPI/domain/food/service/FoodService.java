package com.ssafy.i5i.hotelAPI.domain.food.service;

import java.util.List;

import com.ssafy.i5i.hotelAPI.domain.food.dto.request.FoodRequestDto;
import com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodResponseDto;

public interface FoodService {
	List<FoodResponseDto.TitleD> getFoodFromTravle(FoodRequestDto.Title attractionTitleRequestDto);

	List<FoodResponseDto.Coordi> getFoodFromLngLatv(FoodRequestDto.Coordi attractionCoordiRequestDto);
}
