package com.ssafy.i5i.hotelAPI.domain.food.controller;

import java.util.List;

import com.ssafy.i5i.hotelAPI.common.response.DataResponse;
import com.ssafy.i5i.hotelAPI.domain.elastic.dto.ResponseWikiDto;
import com.ssafy.i5i.hotelAPI.domain.elastic.service.ElasticService;
import com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodTitleResponseDto;
import org.springframework.web.bind.annotation.*;

import com.ssafy.i5i.hotelAPI.domain.food.dto.request.FoodRequestDto;
import com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodResponseDto;
import com.ssafy.i5i.hotelAPI.domain.food.service.FoodServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurant")
@Slf4j
@CrossOrigin(origins = "*")
public class FoodController {
	private final FoodServiceImpl foodServiceImpl;

	@GetMapping("/by-name")
	public DataResponse<List<FoodResponseDto.Coordi>> getFoodFromTravle(
			@RequestParam("name") String name,
			@RequestParam("distance") Double distance,
			@RequestParam(value = "sorted", required = false) String sorted){

		FoodRequestDto.Title attractionTitleRequestDto = new FoodRequestDto.Title(name, distance, sorted);
		List<FoodResponseDto.Coordi> data = foodServiceImpl.getFoodFromTravle(attractionTitleRequestDto);
		return new DataResponse<>(200, "success", data);
	}
	@GetMapping("/by-coordinate")
	public DataResponse<List<FoodResponseDto.Coordi>> getFoodFromLngLatv(
			@RequestParam("longitude") Double longitude,
			@RequestParam("latitude") Double latitude,
			@RequestParam("distance") Double distance,
			@RequestParam("sorted") String sorted){
		FoodRequestDto.Coordi attractionCoordiRequestDto = new FoodRequestDto.Coordi(latitude, longitude, distance, sorted);
		List<FoodResponseDto.Coordi> data = foodServiceImpl.getFoodFromLngLatv(attractionCoordiRequestDto);
		return new DataResponse<>(200, "success", data);
	}
}
