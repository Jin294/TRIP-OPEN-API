package com.sch.sch_elasticsearch.domain.residence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;


@Document(indexName = "accommodation")
@Getter
public class Accommodation {
    @Id
    private String id;
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

}
