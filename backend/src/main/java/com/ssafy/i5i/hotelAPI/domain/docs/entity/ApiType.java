package com.ssafy.i5i.hotelAPI.domain.docs.entity;

import com.ssafy.i5i.hotelAPI.domain.docs.dto.TypeResponseDto;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name="api_type")
public class ApiType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "api_type_id")
    private Long apiTypeId;

    @Column(name="title", length = 300)
    private String title;

    @Column(length = 1000)
    private String detail;

    public TypeResponseDto toDto(){
        return new TypeResponseDto(apiTypeId, title, detail);
    }
}
