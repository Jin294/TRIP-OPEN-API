package com.ssafy.i5i.hotelAPI.domain.food.service;

import java.util.ArrayList;
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
import com.ssafy.i5i.hotelAPI.domain.hotel.dto.request.AttractionCoordinateRequestDto;
import com.ssafy.i5i.hotelAPI.domain.hotel.dto.response.AccommodationResponseDto;
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

	public static final Double EARTH_RADIUS = 6371.0;
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

	// @Override
	// public List<FoodResponseDto> getFoodFromLngLat(AttractionCoordiRequestDto requestDto) {
	// 	return foodRepository.findByCoordinate(requestDto.getLatitude(), requestDto.getLongitude(), requestDto.getDistance())
	// 		.orElseThrow(() -> new CommonException(ExceptionType.NULL_POINT_EXCEPTION))
	// 		.stream()
	// 		.map(FoodResponseDto::new)
	// 		.sorted(getFoodComparator(requestDto.getSorted()))
	// 		.collect(Collectors.toList());
	// }


	@Override
	public List<FoodResponseDto> getFoodFromLngLat(AttractionCoordiRequestDto requestDto) {
		//현재 위도 좌표 (y 좌표)
		double nowLatitude = requestDto.getLatitude();

		//현재 경도 좌표 (x 좌표)
		double nowLongitude = requestDto.getLongitude();

		//km당 y 좌표 이동 값
		double mForLatitude =(1 /(EARTH_RADIUS* 1 *(Math.PI/ 180)));
		//km당 x 좌표 이동 값
		double mForLongitude =(1 /(EARTH_RADIUS* 1 *(Math.PI/ 180)* Math.cos(Math.toRadians(nowLatitude))));

		//현재 위치 기준 검색 거리 좌표
		double maxY = nowLatitude +(requestDto.getDistance()* mForLatitude);
		double minY = nowLatitude -(requestDto.getDistance()* mForLatitude);
		double maxX = nowLongitude +(requestDto.getDistance()* mForLongitude);
		double minX = nowLongitude -(requestDto.getDistance()* mForLongitude);

		return foodRepository.getAroundFoodList(maxY, maxX, minY, minX)
			.orElseThrow(() -> new CommonException(ExceptionType.NULL_POINT_EXCEPTION))
			.stream()
			.map(data -> {
				FoodResponseDto now = data.convertToDto();
				now.setDistance(calculateDistance(requestDto.getLatitude(), requestDto.getLongitude(), now.getFoodLatitude(), now.getFoodLongitude()));
				return now;
			})
			.filter(dto -> dto.getDistance() < requestDto.getDistance())
			.sorted(getFoodComparator(requestDto.getSorted()))
			.collect(Collectors.toList());
	}

	//두 지점 간의 거리 계산
	public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);

		double a = Math.sin(dLat/2)* Math.sin(dLat/2)
			+ Math.cos(Math.toRadians(lat1))* Math.cos(Math.toRadians(lat2))* Math.sin(dLon/2)* Math.sin(dLon/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double d =EARTH_RADIUS* c;
		return d;
	}

	//정렬
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

