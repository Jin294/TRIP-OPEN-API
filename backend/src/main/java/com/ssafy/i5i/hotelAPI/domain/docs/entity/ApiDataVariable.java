package com.ssafy.i5i.hotelAPI.domain.docs.entity;

import com.ssafy.i5i.hotelAPI.domain.docs.dto.VariableDto;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name="api_data_variable")
public class ApiDataVariable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "variable_id")
    private Long id;

    @JoinColumn(name = "api_data")
    @ManyToOne(fetch = FetchType.LAZY)
    private ApiType apiId;

    private String title;
    private String type;
    private String detail;

    @Column(name="is_essential")
    private Boolean isEssential;

    @Column(name="is_request")
    private Boolean isRequest;

    @Column(name="is_parameter")
    private Boolean isParameter;

    public VariableDto toDto(){
        return new VariableDto(title, type, detail, isEssential, isParameter);
    }
}