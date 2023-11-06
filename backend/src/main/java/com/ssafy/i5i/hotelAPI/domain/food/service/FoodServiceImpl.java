package com.ssafy.i5i.hotelAPI.domain.food.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodResponseDto;
import com.ssafy.i5i.hotelAPI.domain.food.repository.FoodRepository;
import com.ssafy.i5i.hotelAPI.domain.hotel.dto.request.AttractionCoordinateRequestDto;
import com.ssafy.i5i.hotelAPI.domain.hotel.dto.request.AttractionNameRequestDto;
import com.ssafy.i5i.hotelAPI.domain.hotel.repository.AttractionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FoodServiceImpl implements FoodService{

	private final FoodRepository foodRepository;
	private final AttractionRepository attractionRepository;

	@Override
	public List<FoodResponseDto> getFoodFromTravle(AttractionNameRequestDto attractionNameRequestDto) {



		return null;
	}

	@Override
	public List<FoodResponseDto> getFoodFromLngLat(AttractionCoordinateRequestDto attractionCoordinateRequestDto) {



		return null;
	}
}
