package com.example.demo.advice.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
/**
 *  - ApiResponse<T>
 *    = 고고맥락의 응답을 주는것                         = 상세하게 어떤 상황인지 구체적인 서술
 *    <> 고맥락의 응답은 ResponseEntity<T> 에서의 상태코드 = 어떤 상황이구나 간편 파악
 */
//           ApiResponse<List<InvalidParameterDto>>
//           ApiResponse<List<ProductResponseDto>>
//           ApiResponse<List<UserResponseDto>>
//           ApiResponse<UserResponseDto>
public class ApiResponse<T> {
    /**
     * 400, 500 등의 실패에 관련된 상태코드임에도 성공한 경우가 있을 수 있고
     * 200, 100 등의 성공에 관련된 상태코드임에도 실패한 경우가 있을 수 있어
     */
    boolean success;
    /**
     * 실패에 대한 상세한 메세지를 줄 수 있습니다 = body 에서 상세한 JSON 통해 안내할수도 있긴함
     * - 예를 들면 401 Unauthorized : 로그인을 실패한것인지? 로그인이 되어있지않은것인지?
     * - 예를 들면 403 Forbidden    : 권한이 아예 없는것인지? 권한이 있는데 일부분만이 없는것인지? 권한 요청이 필요한건지? 아예 접근이 막힌것인지?
     * - 예를 들면 500 Internal Server Error : 데이터베이스측의 문제인지, CDN 측의 문제인지 등을 상세히 표현
     */
    String message;
    /**
     * 여러분들 개발팀 혹은 기획팀에 의해 세부적인 응답코드를 자체적으로 만들 수 있음, 예를 들면
     * - A000 : Auth 인증 혹은 인가 관련된 오류인 경우
     * - B000 : Business 비지니스 관련한 오류인 경우
     * - C000 : Client 클라이언트가 무엇인가 잘못하여 발생한 오류인 경우
     *      - C010 : Client 클라이언트가 (쿠팡 예시의 경우) 판매자인 경우
     *              - C011 : 판매자가 권한이 없는 요청을 하고있는 경우
     *              - C012 : 판매자가 판매할 수 없는 타입의 물건을 업로드하려고하는 경우
     *      - C020 : Client 클라이언트가 (쿠팡 예시의 경우) 구매자인 경우
     * = 문제가 발생했을때 수동으로 고객센터를 통해 문의가 들어온다거나 / 자동으로 프론트엔드에서 로깅을 통해
     *   개발팀에서 대응하기 매우 쉽도록 코드를 분류해놓은것 - 군대에서 암구호 만들어놓은거라고 생각하면됨
     *   의학드라마에도 코드블루 = 긴급상황 등의 ... 개발자가 대처하기 쉽도록 따로 정의한것 -> Open API 스펙서 보면 이런게 있음
     *   - 카카오 Open API 자체 응답코드 예) https://developers.kakao.com/docs/ko/kakaologin/trouble-shooting
     */
//  String status;
    T content;

    // 1.1. 성공 : 반환하고자하는 응답을 반환하시라
    public static <S> ApiResponse<S> success(S content) {
        return new ApiResponse<>(true, null, content);
    }

    // 1.2. 성공 : 반환하고자하는 응답을 반환하시라
    public static <S> ApiResponse<S> success() {
        return success(null);
    }

    // 2.1. 실패 : 반환하고자하는 응답이 있다면 그걸 반환하시고, 메세지에는 그것의 toString() 호출한 값을 넣어라
    public static <F> ApiResponse<F> failure(F content) {
        return failure(content.toString(), content);
    }

    // 2.2. 실패 : 반환하고자하는 응답이 없다면, 메세지만 넣어서 반환하라
    public static ApiResponse<Void> failure(String message) {
        return failure(message, null);
    }

    // 2.3. 실패 : 반환하고자하는 응답도 있고, 개별 메세지도 넣고싶다면 다 넣어서 반환하세요
    public static <F> ApiResponse<F> failure(String message, F content) {
        return new ApiResponse<>(false, message, content);
    }
}
