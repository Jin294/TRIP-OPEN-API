package com.ssafy.i5i.hotelAPI.domain.docs.controller;

import com.ssafy.i5i.hotelAPI.common.response.DataResponse;
import com.ssafy.i5i.hotelAPI.domain.docs.dto.ApiDataDto;
import com.ssafy.i5i.hotelAPI.domain.docs.dto.TypeResponseDto;
import com.ssafy.i5i.hotelAPI.domain.docs.dto.VariableDto;
import com.ssafy.i5i.hotelAPI.domain.docs.service.DocsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/docs/data")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class DocsController {
    private final DocsService docsService;

    @GetMapping("/apilist")
    public DataResponse<?> getApiList() {
        List<ApiDataDto.ApiDataList> data = docsService.getApiList();
        return new DataResponse<>(200, "success", data);
    }

    @GetMapping("/api/variable/{apiId}")
    public DataResponse<?> getVariable(@PathVariable("apiId") Long id) {
        List<VariableDto> data = docsService.getApiVariable(id);
        return new DataResponse<>(200, "success", data);
    }

    @GetMapping("/api/info/{apiId}")
    public DataResponse<?> getApiInfo(@PathVariable("apiId") Long id) {
        ApiDataDto.ApiDataInfo data = docsService.getApiInfo(id);
        return new DataResponse<>(200, "success", data);
    }

    @GetMapping("/apitype")
    public DataResponse<?> getType(){
        List<TypeResponseDto> data = docsService.getTypeList();
        return new DataResponse<>(200, "success", data);
    }

    @GetMapping("/apidata/{api_type_id}")
    public DataResponse<?> getData(@PathVariable("api_type_id") Long id){
        List<ApiDataDto> data = docsService.getApiDataList(id);
        return new DataResponse<>(200, "success", data);
    }
}
