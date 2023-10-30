package com.sch.sch_elasticsearch.domain.residence.service;

import com.sch.sch_elasticsearch.domain.residence.entity.Accommodation;
import com.sch.sch_elasticsearch.domain.residence.repository.AccommodationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class AccommodationService {
    AccommodationRepository accommodationRepository;
    public AccommodationService(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    public Iterable<Accommodation> getAllResidences() {
        Iterable<Accommodation> accommodations = accommodationRepository.findAll();
        return accommodations;
    }

    public List<Accommodation> search(String input) {
        return accommodationRepository.findByAccommodationName(input);
    }

    //지명 유사도 검색
    public
}
