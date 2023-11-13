package com.ssafy.i5i.hotelAPI.domain.docs.service;

import com.ssafy.i5i.hotelAPI.common.exception.CommonException;
import com.ssafy.i5i.hotelAPI.common.exception.ExceptionType;
import com.ssafy.i5i.hotelAPI.domain.docs.dto.ApiDataDto;
import com.ssafy.i5i.hotelAPI.domain.docs.dto.TypeResponseDto;
import com.ssafy.i5i.hotelAPI.domain.docs.entity.ApiData;
import com.ssafy.i5i.hotelAPI.domain.docs.repository.ApiDataRepository;
import com.ssafy.i5i.hotelAPI.domain.docs.repository.ApiTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DocsService {
    private final ApiDataRepository apiDataRepository;
    private final ApiTypeRepository apiTypeRepository;

    public List<ApiDataDto.ApiDataList> getApiList() {
        return apiDataRepository.findAll()
                .stream()
                .map(api -> {
                    return ApiDataDto.ApiDataList.builder()
                            .api_data_id(api.getId())
                            .title(api.getTitle())
                            .content(api.getContent())
                            .method(api.getMethod())
                            .return_type(api.getReturnType())
                            .content_type(api.getContentType())
                            .endpoint(api.getEndpoint())
                            .return_example(api.getReturnExample())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public ApiDataDto.ApiDataInfo getApiInfo(Long apiId) {
        log.info("DocsService getApiInfo, apiId = {}", apiId);
        ApiData apiInfo = apiDataRepository.getApiInfo(apiId).orElseThrow(()->{
            throw new CommonException(ExceptionType.API_TOKEN_EXCEPTION);
        });

        ApiDataDto.ApiDataInfo apiDataInfo = ApiDataDto.ApiDataInfo.setApiInfo(apiInfo);
        return apiDataInfo;
    }

    public List<TypeResponseDto> getTypeList(){
        return apiTypeRepository.findAll()
                .stream()
                .map(apiType -> {return apiType.toDto();})
                .collect(Collectors.toList());
    }

    public List<ApiDataDto> getApiDataList(long type){
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
