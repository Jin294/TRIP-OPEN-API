package com.sch.sch_elasticsearch.domain.wiki.repository;

import com.sch.sch_elasticsearch.domain.accommodation.entity.Accommodation;
import com.sch.sch_elasticsearch.domain.wiki.entity.Wiki;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WikiRepository extends ElasticsearchRepository<Wiki, String> {
    List<Wiki> findByAttractionName(String input);

//    @Query("{\"match\":{\"attraction_name\":{\"query\":\"?0\",\"fuzziness\":1}}}")
//    List<Wiki> findPartialAttractionName(String input);

    @Query("{\"bool\":{\"must\":[{\"match\":{\"attraction_name\":{\"query\":\"?0\",\"fuzziness\":1}}}]}}")
    List<Wiki> findPartialAttractionName(String name);


}
