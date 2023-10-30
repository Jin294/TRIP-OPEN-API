package com.ssafy.i5i.hotelAPI.hotel.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Getter
@Table(name = "accommodation")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Accommodation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "attraction_id")
    private Attraction Attraction;

    @Column(name = "accommodation_name")
    private String accommodationName;

    @Column(name = "accommodation_type")
    private String accommodationType;

    @Column(name = "accommodation_addr")
    private String accommodationAddr;

    @Column(name = "accommodation_score")
    private Double accommodationScore;

    @Column(name = "accommodation_img")
    private String accommodationImg;

    @Column(name = "accommodation_price")
    private String accommodationPrice;

    @Column(name = "accommodation_latitude")
    private BigDecimal accommodationLatitude;

    @Column(name = "accommodation_longitude")
    private BigDecimal accommodationLongitude;
}
