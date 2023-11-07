package com.ssafy.i5i.hotelAPI.domain.food.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.i5i.hotelAPI.common.exception.CommonException;
import com.ssafy.i5i.hotelAPI.common.exception.ExceptionType;
import com.ssafy.i5i.hotelAPI.domain.food.dto.request.AttractionCoordiRequestDto;
import com.ssafy.i5i.hotelAPI.domain.food.dto.request.AttractionTitleRequestDto;
import com.ssafy.i5i.hotelAPI.domain.food.dto.response.FoodResponseDto;
import com.ssafy.i5i.hotelAPI.domain.food.repository.FoodRepository;
import com.ssafy.i5i.hotelAPI.domain.hotel.entity.Attraction;
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
	public List<FoodResponseDto> getFoodFromTravle(AttractionTitleRequestDto requestDto) {
		Attraction attraction = attractionRepository.findByTitle(requestDto.getAttractionName())
			.orElseThrow(() -> {throw new CommonException(ExceptionType.NULL_POINT_EXCEPTION);});

		return foodRepository.findByCoordinate(attraction.getLatitude(), attraction.getLongitude(), requestDto.getDistance())
			.orElseThrow(() -> new CommonException(ExceptionType.NULL_POINT_EXCEPTION))
			.stream()
			.map(FoodResponseDto::new)
			.sorted(getFoodComparator(requestDto.getSorted()))
			.collect(Collectors.toList());
	}

	@Override
	public List<FoodResponseDto> getFoodFromLngLat(AttractionCoordiRequestDto requestDto) {
		return foodRepository.findByCoordinate(requestDto.getLatitude(), requestDto.getLongitude(), requestDto.getDistance())
			.orElseThrow(() -> new CommonException(ExceptionType.NULL_POINT_EXCEPTION))
			.stream()
			.map(FoodResponseDto::new)
			.sorted(getFoodComparator(requestDto.getSorted()))
			.collect(Collectors.toList());
	}

	private Comparator<FoodResponseDto> getFoodComparator(String sortingKey) {
		if ("STAR".equals(sortingKey)) {
			return Comparator.comparing(FoodResponseDto::getFoodStar);
		} else if ("DISTANCE".equals(sortingKey)) {
			return Comparator.comparing(FoodResponseDto::getDistance);
		} else {
			// 기본 정렬 기준을 제공
			return Comparator.comparing(FoodResponseDto::getId);
		}
	}
}
