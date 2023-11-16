package com.sch.sch_elasticsearch.domain.accommodation.controller;

import com.sch.sch_elasticsearch.domain.accommodation.dto.AccommodationDTO;
import com.sch.sch_elasticsearch.domain.accommodation.service.AccommodationService;
import com.sch.sch_elasticsearch.exception.CommonException;
import com.sch.sch_elasticsearch.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accommodation")
public class AccommodationController {

    private final AccommodationService accommodationService;

    //1. 데이터 저장 기능
    @PostMapping("/save")
    public ResponseEntity<String> saveData(@RequestBody AccommodationDTO accommodationDTO) {
        try {
            accommodationService.saveData(accommodationDTO);
            return ResponseEntity.ok("숙소 저장 완료");
        } catch (Exception e) {
            throw new CommonException(ExceptionType.ACCOMMODATION_SAVE_FAIL);
        }
    }

    //2. 데이터 수정 기능
    //

    //3. 제목 검색 후 유사도 출력 : 가장 높음, 상위 N개
    // 추후 데이터 나오면 할 것.



    //3. 전문 검색


}
