package com.ssafy.i5i.hotelAPI.hotel.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "attraction_info")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Long contentId;

    @Column(name = "content_type_id")
    private Long contentTypeId;

    @Column(name = "title")
    private String title;

    @Column(name = "addr1")
    private String addr1;

    @Column(name = "addr2")
    private String addr2;

    @Column(name = "zipcode")
    private Long zipcode;

    @Column(name = "tel")
    private String tel;

    @Column(name = "first_image")
    private String firstImage;

    @Column(name = "first_image2")
    private String firstImage2;

    @Column(name = "readcount")
    private Long readCount;

    @Column(name = "sido_code")
    private Long sidoCode;

    @Column(name = "gugun_code")
    private Long gugunCode;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;

    @Column(name = "mlevel")
    private String mlevel;
}
