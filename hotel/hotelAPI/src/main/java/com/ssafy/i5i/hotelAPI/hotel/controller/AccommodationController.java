package com.ssafy.i5i.hotelAPI.hotel.controller;

import com.ssafy.i5i.hotelAPI.hotel.dto.request.AttractionCoordinateRequestDto;
import com.ssafy.i5i.hotelAPI.hotel.dto.request.AttractionNameRequestDto;
import com.ssafy.i5i.hotelAPI.hotel.dto.response.AccommodationResponseDto;
import com.ssafy.i5i.hotelAPI.hotel.entity.Attraction;
import com.ssafy.i5i.hotelAPI.hotel.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accommodation")
public class AccommodationController {
    private final AccommodationService accommodationService;

    @GetMapping("/{name}/{distance}/{sorted}")
    public List<AccommodationResponseDto> getAccommodationByName(@PathVariable("name") String name, @PathVariable("distance") long distance, @PathVariable("sorted") String sorted){
        AttractionNameRequestDto attractionNameRequestDto = new AttractionNameRequestDto(name, distance, sorted);

        return accommodationService.getAccommodationByName(attractionNameRequestDto);
    }

    @GetMapping("/{latitude}/{longitude}/{distance}/{sorted}")
    public List<AccommodationResponseDto> getAccomodationByCoordinate(@PathVariable("latitude") BigDecimal latitude, @PathVariable("longitude") BigDecimal longitude, @PathVariable("distance") long distance, @PathVariable("sorted") String sorted){
        AttractionCoordinateRequestDto attractionCoordinateRequestDto = new AttractionCoordinateRequestDto(latitude, longitude, distance, sorted);

        return accommodationService.getAccommodationByCoordinate(attractionCoordinateRequestDto);
    }
}
