package com.ssafy.i5i.hotelAPI.domain.docs.controller;

import com.ssafy.i5i.hotelAPI.domain.docs.dto.responseDto.ApiDataResponseDto;
import com.ssafy.i5i.hotelAPI.domain.docs.dto.responseDto.TypeResponseDto;
import com.ssafy.i5i.hotelAPI.domain.docs.service.DocsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/docs/data")
@RequiredArgsConstructor
@Slf4j
public class DocsController {
    private final DocsService docsService;

    @GetMapping("/apitype")
    public List<TypeResponseDto> getType(){
        return docsService.getTypeList();
    }

    @GetMapping("/apidata/{api_type_id}")
    public List<ApiDataResponseDto> getData(@PathVariable("api_type_id") Long id){
        return docsService.getApiDataList(id);
    }



}
