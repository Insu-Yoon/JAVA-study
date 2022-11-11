# Test관련 애너테이션, API

-    @SpringBootTest
    -   통합 테스트에 적합
    -   모든 빈 등록

-   @WebMvcTest
    -   웹 계층 전용 슬라이스 테스트에 적합
    -   웹에 필요한 비난 등록
-    MockMvc : 서블릿 컨테이너 필요 x
    -   요청수행
        -   perform() 
            -   MockMvcRequestBuilders.post
            -   MockMvcRequestBuilders.patch 
    -   request body를 위한 api 메서드
        -   content()
    -    query parameter
        -   params()
    -   response body
        -   andExpect() - 검증
        -   andRetrun() - 결과값 반환 

> **방법론 : given - when - then 패턴으로 작성 (주어질 입력 - 조건 - 결과)**

## **테스트 학습하며 생각할 것**

-   테스트 케이스를 작성하는 습관
    -   F.I.R.S.T 원칙대로 테스트를 시행하는 습관을 들여보자
-   어디서부터 어떻게 테스트 할 것인가 생각하자
    -   막막하다면, given, when, then을 기본으로 생각하고 시작
-   Mock을 사용해야 하는 이유
    -   각 계층의 연결을 끊고=
-   Mockito 사용방법
-   MockMvc 사용방법
