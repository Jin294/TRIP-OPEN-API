package com.sch.sch_elasticsearch.domain.accommodation.dto;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
public class AccommodationDTO {
    private String pkId;
    private String accommodationLng;
    private String accommodationLat;
    private String accommodationName;
    private String accommodationType;
    private String accommodationAddr;
    private String accommodationPic;
    private String accommodationScore;
    private String accommodationPrice;
}
