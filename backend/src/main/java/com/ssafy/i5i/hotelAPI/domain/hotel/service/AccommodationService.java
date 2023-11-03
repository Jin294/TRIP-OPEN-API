package com.ssafy.i5i.hotelAPI.domain.hotel.service;

import com.ssafy.i5i.hotelAPI.common.exception.CommonException;
import com.ssafy.i5i.hotelAPI.common.exception.ExceptionType;
import com.ssafy.i5i.hotelAPI.domain.hotel.entity.Accommodation;
import com.ssafy.i5i.hotelAPI.domain.hotel.entity.Attraction;
import com.ssafy.i5i.hotelAPI.domain.hotel.dto.request.AttractionCoordinateRequestDto;
import com.ssafy.i5i.hotelAPI.domain.hotel.dto.request.AttractionNameRequestDto;
import com.ssafy.i5i.hotelAPI.domain.hotel.dto.response.AccommodationResponseDto;
import com.ssafy.i5i.hotelAPI.domain.hotel.repository.AccommodationRepository;
import com.ssafy.i5i.hotelAPI.domain.hotel.repository.AttractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final AttractionRepository attractionRepository;
    public static final Double RADIUS_OF_EARTH = 6371.0;

    public List<AccommodationResponseDto> sort(List<AccommodationResponseDto> data, String type){
        if(type.equalsIgnoreCase("DISTANCE")){
            data.sort((o1, o2) -> (o1.getRelativeDistance().compareTo(o2.getRelativeDistance())));
        } else if (type.equalsIgnoreCase("SCORE")) {
            data.sort(((o1, o2) -> o1.getAccommodationScore().compareTo(o2.getAccommodationScore())));
        } else if (type.equalsIgnoreCase("TYPE")) {
            data.sort(((o1, o2) -> o1.getAccommodationType().compareTo(o2.getAccommodationType())));
        } else{
            throw new CommonException(ExceptionType.SORTED_TYPE_EXCEPTION);
        }

        return data;
    }
    public List<AccommodationResponseDto> getAccommodationByName(AttractionNameRequestDto requestDto){
        Attraction attraction = attractionRepository.findByTitle(requestDto.getAttractionName())
                .orElseThrow(() -> {throw new CommonException(ExceptionType.NULL_POINT_EXCEPTION);});

        if(attraction == null){
            throw new CommonException(ExceptionType.NULL_POINT_EXCEPTION);
        }
        List<Accommodation> entity = accommodationRepository.findByAttractionId(attraction.getContentId())
                .orElseThrow(() -> {throw new CommonException(ExceptionType.NULL_POINT_EXCEPTION);});

        List<AccommodationResponseDto> response = new ArrayList<>();

        Double distance = requestDto.getDistance();
        for(Accommodation data : entity){
            AccommodationResponseDto now = data.toDto();
            now.setRelativeDistance(calculateDistance(attraction.getLatitude(), attraction.getLongitude(), now.getAccommodationLatitude(), now.getAccommodationLongitude()));
            if(now.getRelativeDistance().compareTo(distance) >= 0) continue;
            response.add(data.toDto());
        }
        //소트
        return response;
    }

    public List<AccommodationResponseDto> getAccommodationByCoordinate(AttractionCoordinateRequestDto requestDto){
        List<Accommodation> entity = accommodationRepository.findByCoordinate(requestDto.getLatitude(), requestDto.getLongitude(), requestDto.getDistance())
                .orElseThrow(() -> {throw new CommonException(ExceptionType.NULL_POINT_EXCEPTION);});

        List<AccommodationResponseDto> response = new ArrayList<>();
        for(Accommodation data : entity){
            AccommodationResponseDto now = data.toDto();
            now.setRelativeDistance(calculateDistance(requestDto.getLatitude(),requestDto.getLongitude(),now.getAccommodationLatitude(),now.getAccommodationLongitude()));
            response.add(now);
        }
        //소트
        return response;
    }


    private Double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
        // 위도와 경도를 사용하여 거리를 계산하는 로직을 구현
        Double lat1Rad = lat1 * Math.PI / 180;
        Double lon1Rad = lon1 * Math.PI / 180;
        Double lat2Rad = lat2 * Math.PI / 180;
        Double lon2Rad = lon2 * Math.PI / 180;

        // Haversine 공식을 사용하여 거리 계산
        Double dLat = Math.abs(lat2Rad - lat1Rad);
        Double dLon = Math.abs(lon2Rad - lon1Rad);
        Double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(dLon / 2), 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double distance = RADIUS_OF_EARTH * c;

        return distance;
    }

}
