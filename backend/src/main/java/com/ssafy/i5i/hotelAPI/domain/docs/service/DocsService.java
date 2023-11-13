package com.ssafy.i5i.hotelAPI.domain.docs.service;

import com.ssafy.i5i.hotelAPI.common.exception.CommonException;
import com.ssafy.i5i.hotelAPI.common.exception.ExceptionType;
import com.ssafy.i5i.hotelAPI.domain.docs.dto.ApiDataResponseDto;
import com.ssafy.i5i.hotelAPI.domain.docs.dto.TypeResponseDto;
import com.ssafy.i5i.hotelAPI.domain.docs.repository.ApiDataRepository;
import com.ssafy.i5i.hotelAPI.domain.docs.repository.ApiTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DocsService {
    private final ApiDataRepository apiDataRepository;
    private final ApiTypeRepository apiTypeRepository;

    public List<TypeResponseDto> getTypeList(){
        return apiTypeRepository.findAll()
                .stream()
                .map(apiType -> {return apiType.toDto();})
                .collect(Collectors.toList());
    }

    public List<ApiDataResponseDto> getApiDataList(long type){
//        return apiDataRepository.getApiDataById(type)
//                .orElseThrow(() -> {throw new CommonException(ExceptionType.NULL_POINT_EXCEPTION);})
//                .stream()
//                .map(data -> {
//                    return data.toDto();
//                })
//                .collect(Collectors.toList());
        return null;
    }
}
