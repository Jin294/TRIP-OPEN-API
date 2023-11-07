package com.sch.sch_elasticsearch.domain.wiki.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "scrap_wiki_1102")
@Mapping(mappingPath = "jsonlist/wiki/wiki-mapping.json")
@Setting(settingPath = "jsonlist/wiki/wiki-setting.json")
@Getter
public class Wiki {
    @Id
    private String id;

    @Field(name = "pk_id")
    String pkId;

    @Field(name = "content_id")
    String contentId;

    @Field(name = "attraction_name")
    String attractionName;

    @Field(name = "number")
    String number;

    @Field(name = "wiki_title")
    String wiki_title;

    @Field(name = "wiki_content")
    String wiki_content;

    @Field(name = "overview")
    String overview;

}
