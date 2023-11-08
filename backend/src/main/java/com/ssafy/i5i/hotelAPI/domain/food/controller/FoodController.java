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
import com.ssafy.i5i.hotelAPI.domain.food.entity.Food;
import com.ssafy.i5i.hotelAPI.domain.food.repository.FoodRepository;
import com.ssafy.i5i.hotelAPI.domain.food.service.FoodServiceImpl;
import com.ssafy.i5i.hotelAPI.domain.hotel.dto.request.AttractionCoordinateRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/food")
@Slf4j
public class FoodController {
	private final FoodServiceImpl foodServiceImpl;


	@GetMapping("/{name}/{distance}/{sorted}")
	public List<FoodResponseDto> getFoodFromTravle(@PathVariable("name")String name, @PathVariable("distance")Long distance, @PathVariable("sorted")String sorted ){
		AttractionTitleRequestDto attractionTitleRequestDto = new AttractionTitleRequestDto(name, distance, sorted);
		return foodServiceImpl.getFoodFromTravle(attractionTitleRequestDto);
	}


	@GetMapping("/v1/{longtitude}/{latitude}/{distance}/{sorted}")
	public List<FoodResponseDto> getFoodFromLngLatv1(@PathVariable("longtitude")Double longitude, @PathVariable("latitude")Double latitude, @PathVariable("distance")Long distance, @PathVariable("sorted")String sorted){
		AttractionCoordiRequestDto attractionCoordiRequestDto = new AttractionCoordiRequestDto(latitude, longitude, distance, sorted);
		return foodServiceImpl.getFoodFromLngLatv1(attractionCoordiRequestDto);
	}

	@GetMapping("/v2/{longtitude}/{latitude}/{distance}/{sorted}")
	public List<FoodResponseDto> getFoodFromLngLatv2(@PathVariable("longtitude")Double longitude, @PathVariable("latitude")Double latitude, @PathVariable("distance")Long distance, @PathVariable("sorted")String sorted){
		AttractionCoordiRequestDto attractionCoordiRequestDto = new AttractionCoordiRequestDto(latitude, longitude, distance, sorted);
		return foodServiceImpl.getFoodFromLngLatv2(attractionCoordiRequestDto);
	}

	@GetMapping("/v3/{longtitude}/{latitude}/{distance}/{sorted}")
	public List<FoodResponseDto> getFoodFromLngLatv3(@PathVariable("longtitude")Double longitude, @PathVariable("latitude")Double latitude, @PathVariable("distance")Long distance, @PathVariable("sorted")String sorted){
		AttractionCoordiRequestDto attractionCoordiRequestDto = new AttractionCoordiRequestDto(latitude, longitude, distance, sorted);
		return foodServiceImpl.getFoodFromLngLatv3(attractionCoordiRequestDto);
	}


	// @GetMapping("/v5/{longtitude}/{latitude}/{distance}/{sorted}")
	// public List<FoodResponseDto> getFoodFromLngLatv5(@PathVariable("longtitude")Double longitude, @PathVariable("latitude")Double latitude, @PathVariable("distance")Long distance, @PathVariable("sorted")String sorted){
	// 	AttractionCoordiRequestDto attractionCoordiRequestDto = new AttractionCoordiRequestDto(latitude, longitude, distance, sorted);
	// 	return foodServiceImpl.getFoodFromLngLatv5(attractionCoordiRequestDto);
	// }

	@GetMapping("/test")
	public String getString(){
		String data = "Test";
		return data;
	}

	@GetMapping("/name/{name}")
	public Food findFoodByName(@PathVariable("name")String name){
		return foodServiceImpl.findFoodByName(name);
	}

	@GetMapping("/idx/{idx}")
	public Food findFoodByIdx(@PathVariable("idx")Long idx){
		long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기

		//실험할 코드 추가
		Food result = foodServiceImpl.findFoodByIdx(idx);

		long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
		long secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산

		log.info("api 속도(ms) : {} ",secDiffTime);

		return result;
		// return foodServiceImpl.findFoodByIdx(idx);
	}

}
