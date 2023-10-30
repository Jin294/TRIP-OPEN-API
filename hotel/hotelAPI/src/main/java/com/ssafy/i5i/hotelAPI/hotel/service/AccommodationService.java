package com.ssafy.i5i.hotelAPI.hotel.service;

import com.ssafy.i5i.hotelAPI.hotel.dto.request.AttractionCoordinateRequestDto;
import com.ssafy.i5i.hotelAPI.hotel.dto.request.AttractionNameRequestDto;
import com.ssafy.i5i.hotelAPI.hotel.dto.response.AccommodationResponseDto;
import com.ssafy.i5i.hotelAPI.hotel.entity.Accommodation;
import com.ssafy.i5i.hotelAPI.hotel.entity.Attraction;
import com.ssafy.i5i.hotelAPI.hotel.repository.AccommodationRepository;
import com.ssafy.i5i.hotelAPI.hotel.repository.AttractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final AttractionRepository attractionRepository;
    public List<AccommodationResponseDto> getAccommodationByName(AttractionNameRequestDto requestDto){
        Attraction attraction = attractionRepository.findByTitle(requestDto.getAttractionName());
        List<Accommodation> entity = accommodationRepository.findByAttractionId(attraction.getContentId());
        List<AccommodationResponseDto> response = new ArrayList<>();

        for(Accommodation data : entity){
            response.add(data.toDto());
        }
        //소트

        return response;
    }
    public List<AccommodationResponseDto> getAccommodationByCoordinate(AttractionCoordinateRequestDto requestDto){
        List<Accommodation> entity = accommodationRepository.findByCoordinate(requestDto.getLatitude(), requestDto.getLongitude(), requestDto.getDistance());

        List<AccommodationResponseDto> response = new ArrayList<>();
        for(Accommodation data : entity){
            response.add(data.toDto());
        }
        //소트
        return response;
    }
}
