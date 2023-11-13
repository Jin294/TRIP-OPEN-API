package com.ssafy.i5i.hotelAPI.domain.food.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ssafy.i5i.hotelAPI.domain.food.dto.request.FoodRequestDto;
import com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodResponseDto;
import com.ssafy.i5i.hotelAPI.domain.food.service.FoodServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/food")
@Slf4j
public class FoodController {
	private final FoodServiceImpl foodServiceImpl;
	@GetMapping("/by-name")
	public List<FoodResponseDto.Coordi> getFoodFromTravle(
			@RequestParam("name") String name,
			@RequestParam("distance") Long distance,
			@RequestParam("sorted") String sorted){
		FoodRequestDto.Title attractionTitleRequestDto = new FoodRequestDto.Title(name, distance, sorted);
		return foodServiceImpl.getFoodFromTravle(attractionTitleRequestDto);
	}
	@GetMapping("/by-coordinate")
	public List<FoodResponseDto.Coordi> getFoodFromLngLatv(
			@RequestParam("longtitude") Double longitude,
			@RequestParam("latitude") Double latitude,
			@RequestParam("distance") Long distance,
			@RequestParam("sorted") String sorted){
		FoodRequestDto.Coordi attractionCoordiRequestDto = new FoodRequestDto.Coordi(latitude, longitude, distance, sorted);
		return foodServiceImpl.getFoodFromLngLatv(attractionCoordiRequestDto);
	}
}
