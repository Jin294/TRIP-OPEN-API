package com.sch.sch_elasticsearch.domain.test;

import com.sch.sch_elasticsearch.domain.accommodation.entity.Accommodation;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepo extends ElasticsearchRepository<TestDocs, String> {
    List<TestDocs> findByName(String name);

    @Query("{\"match\":{\"accommodation_name\":\"?0\"}}")
    List<Accommodation> querytest2(String name);
}
