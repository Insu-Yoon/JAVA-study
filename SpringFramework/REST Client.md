> **짚고 넘어가기  
> 서버 - 클라이언트 아키텍처에서 각 관계는 상대적인 것이다.  
> 서버 A가 클라이언트의 요청을 처리하기 위해 서버 B의 리소스를 사용한다면, 서버 A는 클라이언트의 역할을 하게 된다.**

# **Rest Client**

>  **Rest API 서버에 HTTP 요청을 보낼 수 있는 클라이언트 툴 또는 라이브러리**

-   Postman 또한 'UI가 갖춰진 Rest Client' 에 속한다.

당연히 모든 상황에서 UI가 갖춰진 Rest Client로 처리할 수는 없을 것이다.

-   특정 애플리케이션의 내부에서 다른 애플리케이션에 HTTP 요청을 보내야 한다면?
    -   Rest Client 라이브러리를 사용하면 된다.

# **RestTemplate**

> **Spring에서 지원하는 Rest Client API.  
> Java에서 사용할 수 있는 HTTP Client 라이브러리 중 하나를 이용하여, 다른 백엔드 서버에 HTTP 요청을 보낼 수 있게 해준다.**

-   기능
    -   Rest 엔드 포인트 지정
    -   헤더 설정
    -   파라미터 및 body 설정

## **사용법**

1.  RestTemplate 객체 생성
2.  URI 생성
3.  Request 전송

코드로 살펴보자.

```java
public class RestClientTest {
    public static void main(String[] args) {
        //RestTemplate 객체 생성
        RestTemplate restTemplate =
                new RestTemplate(new HttpComponentsClientHttpRequestFactory());

        //URI 생성
        UriComponents uriComponents =
                UriComponentsBuilder
                        .newInstance()
                        .scheme("http")
                        .host("worldtimeapi.org")
                          //디폴트가 80이므로, 80포트를 사용한다면 생략 가능
//                        .port(80)
                        .path("/api/timezone/{continents}/{city}")
                        .encode()
                        .build();
        URI uri = uriComponents.expand("Asia", "Seoul").toUri();

        // Request 전송. Response 클래스로 응답 데이터를 전달 받는다.
        Response response = restTemplate.getForObject(uri, Response.class);

        System.out.println("# datatime: " + worldTime.getDatetime());
        System.out.println("# timezone: " + worldTime.getTimezone());
        System.out.println("# day_of_week: " + worldTime.getDay_of_week());
    }
}
```

-   newInstance()
    -   UriComponentBuilder 객체를 생성
-   scheme()
    -   URI의 scheme를 설정
-   host()
    -   호스트 정보 입력
-   port()
    -   포트 정보 입력
-   path()
    -   URI의 경로 입력
    -   템플릿 변수 {변수명} 사용 가능
    -   위 코드에서 {continents}와 {city}는 uriComponents.expand("Asia", "Seoul").toUri(); 에서 expand() 의 파라미터 문자열로 치환된다.(빌드 타임에 변환)
-   encode()
    -   URI에 사용된 템플릿 변수들을 인코딩 해줌
-   build()
    -   UriComponents 객체를 생성
-   getForObject(URI uri, Class<T> responseType)
    -   HTTP Get 요청을 통해 서버의 리소스를 조회
    -   URI uri : 요청을 전송할 엔드포인트의 URI 객체 지정
    -   Class<T> responseType : 응답으로 전달받을 클래스의 타입 지정

> **응답 데이터를 DTO 클래스로 받아올 수 있다.  
> DTO 클래스에서 필드 변수로 무엇을 두냐를 통해 원하느 데이터만 전달 받을 수 있다.**
