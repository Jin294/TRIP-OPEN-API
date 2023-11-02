package com.sch.sch_elasticsearch.domain.wiki.service;

import com.sch.sch_elasticsearch.domain.wiki.entity.Wiki;
import com.sch.sch_elasticsearch.domain.wiki.repository.WikiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WikiService {
    private final WikiRepository wikiRepository;

    public List<Wiki> searchExactName(String input) {
        return wikiRepository.findByAttractionName(input);
    }

    public List<Wiki> searchPartialName(String input) {
        return wikiRepository.findPartialAttractionName(input);
    }
}
