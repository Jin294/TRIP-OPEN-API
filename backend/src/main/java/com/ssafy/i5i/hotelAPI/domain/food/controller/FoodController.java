package com.ssafy.i5i.hotelAPI.domain.food.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


	@GetMapping("/{name}/{distance}/{sorted}")
	public List<FoodResponseDto.Coordi> getFoodFromTravle(@PathVariable("name")String name, @PathVariable("distance")Long distance, @PathVariable("sorted")String sorted ){
		FoodRequestDto.Title attractionTitleRequestDto = new FoodRequestDto.Title(name, distance, sorted);
		return foodServiceImpl.getFoodFromTravle(attractionTitleRequestDto);
	}

	@GetMapping("/{longtitude}/{latitude}/{distance}/{sorted}")
	public List<FoodResponseDto.Coordi> getFoodFromLngLatv(@PathVariable("longtitude")Double longitude, @PathVariable("latitude")Double latitude, @PathVariable("distance")Long distance, @PathVariable("sorted")String sorted){
		FoodRequestDto.Coordi attractionCoordiRequestDto = new FoodRequestDto.Coordi(latitude, longitude, distance, sorted);
		return foodServiceImpl.getFoodFromLngLatv(attractionCoordiRequestDto);
	}
}
