package com.ssafy.i5i.hotelAPI.hotel.service;

import com.ssafy.i5i.hotelAPI.hotel.common.exception.CommonException;
import com.ssafy.i5i.hotelAPI.hotel.common.exception.ExceptionType;
import com.ssafy.i5i.hotelAPI.hotel.common.exception.GlobalExceptionAdvice;
import com.ssafy.i5i.hotelAPI.hotel.dto.request.AttractionCoordinateRequestDto;
import com.ssafy.i5i.hotelAPI.hotel.dto.request.AttractionNameRequestDto;
import com.ssafy.i5i.hotelAPI.hotel.dto.response.AccommodationResponseDto;
import com.ssafy.i5i.hotelAPI.hotel.entity.Accommodation;
import com.ssafy.i5i.hotelAPI.hotel.entity.Attraction;
import com.ssafy.i5i.hotelAPI.hotel.repository.AccommodationRepository;
import com.ssafy.i5i.hotelAPI.hotel.repository.AttractionRepository;
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
    public static final BigDecimal RADIUS_OF_EARTH = new BigDecimal("6371.0");

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

        BigDecimal distance = new BigDecimal(requestDto.getDistance());
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


    private BigDecimal calculateDistance(BigDecimal lat1, BigDecimal lon1, BigDecimal lat2, BigDecimal lon2) {
        // 위도와 경도를 사용하여 거리를 계산하는 로직을 구현
        BigDecimal lat1Rad = lat1.multiply(new BigDecimal(Math.PI)).divide(new BigDecimal(180), MathContext.DECIMAL128);
        BigDecimal lon1Rad = lon1.multiply(new BigDecimal(Math.PI)).divide(new BigDecimal(180), MathContext.DECIMAL128);
        BigDecimal lat2Rad = lat2.multiply(new BigDecimal(Math.PI)).divide(new BigDecimal(180), MathContext.DECIMAL128);
        BigDecimal lon2Rad = lon2.multiply(new BigDecimal(Math.PI)).divide(new BigDecimal(180), MathContext.DECIMAL128);

        // Haversine 공식을 사용하여 거리 계산
        BigDecimal dLat = lat2Rad.subtract(lat1Rad).abs();
        BigDecimal dLon = lon2Rad.subtract(lon1Rad).abs();
        BigDecimal a = new BigDecimal(Math.pow(Math.sin(dLat.doubleValue() / 2), 2), MathContext.DECIMAL128)
                .add(new BigDecimal(Math.cos(lat1Rad.doubleValue())
                        * Math.cos(lat2Rad.doubleValue())
                        * Math.pow(Math.sin(dLon.doubleValue() / 2), 2), MathContext.DECIMAL128));
        BigDecimal c = new BigDecimal(2 * Math.atan2(Math.sqrt(a.doubleValue()), Math.sqrt(1 - a.doubleValue())), MathContext.DECIMAL128);
        BigDecimal distance = RADIUS_OF_EARTH.multiply(c, MathContext.DECIMAL128);

        return distance;
    }

}
