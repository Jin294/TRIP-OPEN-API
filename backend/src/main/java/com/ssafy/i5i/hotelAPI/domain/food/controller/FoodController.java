package com.ssafy.i5i.hotelAPI.domain.food.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.i5i.hotelAPI.domain.food.dto.request.AttractionCoordiRequestDto;
import com.ssafy.i5i.hotelAPI.domain.food.dto.request.AttractionTitleRequestDto;
import com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodResponseDto;
import com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodResponseDtoInterface;
import com.ssafy.i5i.hotelAPI.domain.food.service.FoodServiceImpl;
import com.ssafy.i5i.hotelAPI.domain.hotel.dto.request.AttractionCoordinateRequestDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/food")
public class FoodController {
	private final FoodServiceImpl foodServiceImpl;

	@GetMapping("/{name}/{distance}/{sorted}")
	public List<FoodResponseDto> getFoodFromTravle(@PathVariable("name")String name, @PathVariable("distance")Long distance, @PathVariable("sorted")String sorted ){
		AttractionTitleRequestDto attractionTitleRequestDto = new AttractionTitleRequestDto(name, distance, sorted);
		return foodServiceImpl.getFoodFromTravle(attractionTitleRequestDto);
	}

	@GetMapping("/{longtitude}/{latitude}/{distance}/{sorted}")
	public List<FoodResponseDto> getFoodFromLngLat(@PathVariable("longtitude")Double longitude, @PathVariable("latitude")Double latitude, @PathVariable("distance")Long distance, @PathVariable("sorted")String sorted){
		AttractionCoordiRequestDto attractionCoordiRequestDto = new AttractionCoordiRequestDto(latitude, longitude, distance, sorted);
		return foodServiceImpl.getFoodFromLngLat(attractionCoordiRequestDto);
	}

	@GetMapping("/test")
	public String getString(){
		String data = "Test";
		return data;
	}

}
