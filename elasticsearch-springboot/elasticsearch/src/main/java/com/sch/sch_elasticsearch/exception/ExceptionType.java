package com.sch.sch_elasticsearch.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {
    /**
     * CODE : 4자리 양의 정수
     * MESSAGE : 예외 메시지
     */

    /** Wiki Domain : 0xxx **/
    /** WikiServiceExtend : 02xx **/
    GET_SAME_ATTNAME_AND_WIKITITLE_FAIL(0201, "동일 제목 결과를 찾는 데 실패하였습니다."),
    FUZZINESS_SEARCH_FAIL(0202, "fuzziness 검색에 실패하였습니다."),
    SEARCH_ALL_BY_DTO_FAIL(0203, "가장 유사도가 높은 내용을 검색하는 데 실패하였습니다. "),
    SEARCH_ALL_BY_PARAM_FAIL(0204, "가장 유사도가 높은 내용을 검색하는 데 실패하였습니다. "),

    /** WikiServiceTermSimilary : 03xx **/
    USE_ANALYZER_AND_GET_TERMS_FAIL(0301, "분석기를 사용한 용어 단위 분석이 실패했습니다."),
    FIND_WIKI_NOT_EXIST_TERMS(0302, "Term 필드가 Null인 도큐먼트들을 가져오는데 실패했습니다."),
    CHECK_CONTENT_IS_NULL(0303,
            "term 필드가 null인 값 중 wiki_countent가 빈 값들의 term 값 삽입을 실패했습니다."),
    CALCULATE_SIMILARY_OVERVIEW_AND_CONTENT(0304, "필드 간의 문장 유사성을 비교하는 데 실패했습니다."),
    UPDATE_NULL_TERMS_FAIL(0304, "새로운 term 계산 값 갱신에 실패하였습니다." ),

    /** WikiServiceTitle : 04xx**/
    SEARCH_CORRECT_TITLE_FAIL(0401, "일치하는 제목을 찾는 중 오류가 발생했습니다." ),
    SEARCH_FUZZY_TITLE_FAIL(0402, "Fuzzyniss 제목 유사 검색이 실패하였습니다."),
    SEARCH_NGRAM_TITLE_FAIL(0403, "Ngram 제목 유사 검색이 실패하였습니다."),
    SEARCH_FUZZY_AND_NGRAM_TITLE_FAIL(0404, "Fuzzy 검색과 Ngram 통합 검색을 집계하는 과정이 실패했습니다."),


    /** ETC : 05xx **/
    /** WikiController**/
    AGGREGATE_TITLE_SEARCH_FAIL(0501, "제목 검색 : 제목 일치 or (Fuzzy + ngram) 에 실패하였습니다."),
    /** CheckParameterInterceptor **/
    INTERCEPTOR_TOO_MANY_MAX_RESULTS(0502, "너무 많은 maxResult 매개변수입니다. (100 초과)"),
    /** ToolsForWikiService **/
    TYPENUM_IS_INVALID(0503, "키워드 검색용 TypeNum 번호가 유효하지 않습니다."),


    

    /** Accommodation Domain : 2xxx **/
    ACCOMMODATION_SAVE_FAIL(2008, "숙소 데이터 저장 실패"),




    ;
    private final int code;
    private final String message;
}
