package com.sch.sch_elasticsearch.domain.accommodation.service;

import com.sch.sch_elasticsearch.domain.accommodation.dto.AccommodationDTO;
import com.sch.sch_elasticsearch.domain.accommodation.entity.Accommodation;
import com.sch.sch_elasticsearch.domain.accommodation.repository.AccommodationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;


    public void saveData(AccommodationDTO accommodationDTO) {
        Accommodation accommodation = new Accommodation(
                accommodationDTO.getPkId(),
                accommodationDTO.getAccommodationLng(),
                accommodationDTO.getAccommodationLat(),
                accommodationDTO.getAccommodationName(),
                accommodationDTO.getAccommodationType(),
                accommodationDTO.getAccommodationAddr(),
                accommodationDTO.getAccommodationPic(),
                accommodationDTO.getAccommodationScore(),
                accommodationDTO.getAccommodationPrice()
        );
        accommodationRepository.save(accommodation);
    }
    //지명 유사도 검색
}
