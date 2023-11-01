package com.sch.sch_elasticsearch.domain.test;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepo testRepo;

    //데이터 삽입
    public void saveTest(TestDTO testDTO) {
        TestDocs testDocs = new TestDocs();
//        testDocs.id = testDTO.getId(); //id 동적 매핑
        testDocs.name = testDTO.getName();

        testRepo.save(testDocs);
    }
}
