package com.ssafy.i5i.hotelAPI.domain.hotel.controller;

import com.ssafy.i5i.hotelAPI.domain.hotel.dto.request.AttractionCoordinateRequestDto;
import com.ssafy.i5i.hotelAPI.domain.hotel.dto.request.AttractionNameRequestDto;
import com.ssafy.i5i.hotelAPI.domain.hotel.dto.response.AccommodationResponseDto;
import com.ssafy.i5i.hotelAPI.domain.hotel.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accommodation")
@CrossOrigin(origins = "*")
public class AccommodationController {
    private final AccommodationService accommodationService;

    @GetMapping("/by-name")
    public List<AccommodationResponseDto> getAccommodationByName(
            @RequestParam("name") String name,
            @RequestParam("distance") Double distance,
            @RequestParam("sorted") String sorted){
        AttractionNameRequestDto attractionNameRequestDto = new AttractionNameRequestDto(name, distance, sorted);
        System.out.println("!!!!");
        return accommodationService.getAccommodationByName(attractionNameRequestDto);
    }

    @GetMapping("/by-coordinate")
    public List<AccommodationResponseDto> getAccomodationByCoordinate(
            @RequestParam("latitude") Double latitude,
            @RequestParam("longtitude") Double longitude,
            @RequestParam("distance") Double distance,
            @RequestParam("sorted") String sorted){
        AttractionCoordinateRequestDto attractionCoordinateRequestDto = new AttractionCoordinateRequestDto(latitude, longitude, distance, sorted);

        return accommodationService.getAccommodationByCoordinate(attractionCoordinateRequestDto);
    }
}
