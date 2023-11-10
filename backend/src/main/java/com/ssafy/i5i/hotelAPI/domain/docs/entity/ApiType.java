package com.ssafy.i5i.hotelAPI.domain.docs.entity;

import com.ssafy.i5i.hotelAPI.domain.docs.dto.responseDto.TypeResponseDto;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name="api_type")
public class ApiType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apt_type_id")
    private Long id;

    private String title;

    @Column(length = 1000)
    private String detail;

    public TypeResponseDto toDto(){
        return new TypeResponseDto(id, title, detail);
    }
}
