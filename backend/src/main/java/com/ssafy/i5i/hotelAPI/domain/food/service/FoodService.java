package com.ssafy.i5i.hotelAPI.domain.food.service;

import java.util.List;

import com.ssafy.i5i.hotelAPI.domain.food.dto.request.FoodRequestDto;
import com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodResponseDto;
import com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodeResponseDtov2;

public interface FoodService {
	List<FoodResponseDto.Coordi> getFoodFromTravle(FoodRequestDto.Title attractionTitleRequestDto);
	List<FoodResponseDto.Coordi> getFoodFromLngLatv(FoodRequestDto.Coordi attractionCoordiRequestDto);
	List<FoodeResponseDtov2> getFoodFromLngLatvv1(FoodRequestDto.Coordi attractionCoordiRequestDto);
	List<FoodeResponseDtov2> getFoodFromLngLatvv2(FoodRequestDto.Coordi attractionCoordiRequestDto);
}
