package com.sch.sch_elasticsearch.domain.accommodation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;


@Document(indexName = "test_accommo_list")
@Mapping(mappingPath = "jsonlist/accommodation/accommodation-mapping.json")
@Setting(settingPath = "jsonlist/accommodation/accommodation-setting.json")
public class Accommodation {
    @Id
    private String id;
    @Field(name = "accommodation_id")
    private String pkId;
    @Field(name = "accommodation_lng")
    private String accommodationLng;
    @Field(name = "accommodation_lat")
    private String accommodationLat;
    @Field(name = "accommodation_name")
    private String accommodationName;
    @Field(name = "accommodation_type")
    private String accommodationType;
    @Field(name = "accommodation_addr")
    private String accommodationAddr;
    @Field(name = "accommodation_pic")
    private String accommodationPic;
    @Field(name = "accommodation_score")
    private String accommodationScore;
    @Field(name = "accommodation_price")
    private String accommodationPrice;

    public Accommodation(String pkId, String accommodationLng, String accommodationLat, String accommodationName, String accommodationType
    , String accommodationAddr, String accommodationPic, String accommodationScore, String accommodationPrice) {
        this.pkId = pkId;
        this.accommodationLng = accommodationLng;
        this.accommodationLat = accommodationLat;
        this.accommodationAddr = accommodationAddr;
        this.accommodationPrice = accommodationPrice;
        this.accommodationScore = accommodationScore;
        this.accommodationName = accommodationName;
        this.accommodationType = accommodationType;
        this.accommodationPic = accommodationPic;
    }
}
