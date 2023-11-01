package com.sch.sch_elasticsearch.domain.test;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "test_idx")
@Setting(settingPath = "jsonlist/test/test-setting.json")
@Mapping(mappingPath = "jsonlist/test/test-mapping.json")
public class TestDocs {

    @Id
    String id;

    @Field(name = "testName")
    String name;
}
