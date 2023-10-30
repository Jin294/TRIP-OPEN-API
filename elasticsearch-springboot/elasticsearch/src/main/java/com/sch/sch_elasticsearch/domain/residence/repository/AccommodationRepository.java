package com.sch.sch_elasticsearch.domain.residence.repository;

import com.sch.sch_elasticsearch.domain.residence.entity.Accommodation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends ElasticsearchRepository<Accommodation, String> {
    List<Accommodation> findByAccommodationName(String name);
}
