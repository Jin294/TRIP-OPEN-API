package com.ssafy.i5i.hotelAPI.domain.docs.entity;

import com.ssafy.i5i.hotelAPI.domain.docs.dto.VariableDto;
import com.ssafy.i5i.hotelAPI.domain.docs.dto.ApiDataDto;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Table(name="api_data")
public class ApiData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "api_id")
    private Long id;

    @JoinColumn(name = "api_type_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ApiType apiType;

    @OneToMany(mappedBy = "apiId", fetch = FetchType.LAZY)
    private List<ApiDataVariable> variable = new ArrayList<>();

    private String title;
    private String content;

    @Column(length = 10)
    private String method;

    @Column(name="return_type", length = 5)
    private String returnType;

    @Column(name="content_type", length = 10)
    private String contentType;

    private String endpoint;

    @Column(name = "return_example")
    private String returnExample;
}
