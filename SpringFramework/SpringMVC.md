# **Spring MVC**

> **M - Model**  
> **V - View**  
> **C - Controller**

Spring Framework의 모듈 중 웹 계층을 담당하는 몇개의 모듈이 존재한다. 그 중 서블릿(Servlet) API를 기반으로 클라이언트의 요청을 처리하는 모듈이 있는데, 이 모듈이 spring-webmvc이다.

spring-webmvc를 줄여서 Spring MVC라고 부른다.

> **Spring MVC : 클라이언트의 요청을 편리하게 처리해주는 프레임 워크**

> **서블릿(Servlet) : 클라이언트의 요청을 처리하도록, 특정 규약에 맞춰 Java 코드로 작성하는 클래스 파일**

## **Model**

-   **클라이언트에게 응답으로 돌려줄, **작업의 처리 결과 데이터** \= Model !!**

웹 앱이 클라이언트의 요청을 전달 받아, 요청 사항을 전달하기 위한 작업을 한다. 그 이후 작업 처리 결과를 클라이언트에게 응답으로 돌려준다.

> **서비스 계층(Service Layer) : 요청 사항을 처리하는 영역  
> 비즈니스 로직(Business Logic) : 요청 사항을 처리하기 위해 Java 코드로 구현한 것**

## **View**

-   Model 데이터를 이용하여, 클라이언트 애플리케이션의 화면에 보여질 리소스를 제공
    -   HTML 페이지의 출력  
        클라이언트 앱에 보여질 HTML페이지를 직접 렌더링하여 전송하는 방식(=SSR?)
    -   PDF, Excel 등의 문서 형태로 출력
    -   ****XML, JSON 등 특정 형식의 포맷으로의 변환****
        -   Model 데이터를 특정 프로토콜 형태로 변환하여 클라이언트 측에 전송하는 방식
        -   프런트엔드에서 전송된 데이터로 HTML 페이지를 만든다.
        -   프론트엔드와 백엔드의 구분이 명확하므로 개발 및 유지보수가 상대적으로 용이

이 중, 마지막의 특정 형식 포맷으로의 변환을 집중적으로 학습하게 될 것이다.

현재는 XML보다 상대적으로 가볍고 복잡하지 않은 JSON 형식을 사용하는 추세이다.

> **JSON(JavaScript Object Notation) : 클라이언트 앱과 서버 앱이 주고받는 데이터 형식  
> 기본 포맷 : { "속성" : "값" }**

## **Controller**

-   클라이언트의 요청을 직접적으로 전달 받는 엔드포인트
-   Model과 View의 중간에서 상호작용을 해주는 역할 수행

# **SpringMVC의 동작 흐름**

![image](https://user-images.githubusercontent.com/110891599/196981663-3f87ec00-ef4b-47d7-b699-99dbe6424d8b.png)
[이미지1]

위의 이미지와 함께 SpringMVC이 요청을 처리하는 흐름을 파악해보자.

1.  클라이언트가 전송한 요청이 DispatcherServlet 클래스에 전달된다.
2.  DispatcherServlet은 요청을 처리할 Controller에 대한 검색을 HandlerMapping 인터페이스에 요청한다.  
    HandlerMapping은 클라이언트 요청과 매핑되는 핸들러 객체를 DispatcherServlet에게 반환한다.
    -   이때, 핸들러 객체는 해당 핸들러의 Handler 메서드 정보를 포함한다.
    -   Handler 메서드 : Controller 클래스 안에 구현된 요청처리 메서드
3.  DispatcherServlet은 HandlerAdapter에게 Handler 메서드 호출을 위임한다.
4.  HandlerAdapter는 DispatcherServlet으로부터 전달받은 Controller 정보를 기반으로, 해당 Controller의 Handler 메서드를 호출한다.
5.  Controller의 Handler 메서드는 비즈니스 로직 처리 후, 반환 받은 Model 데이터를 HandlerAdapter에게 전달한다.  
    HandlerAdapter는 Model 데이터와 View 정보를 DispatcherServlet에게 전달한다.
6.  DispatcherServlet은 전달받은 View 정보를 다시 ViewResolver에게 전달하여, View 검색을 요청한다.  
    ViewResolver는 View 정보에 해당하는 View를 찾아서 View를 반환해준다.
7.  DispatcherServlet은 ViewResolver로부터 전달받은 View 객체를 통해 Model 데이터를 넘겨주면서, 클라이언트에게 전달할 응답 데이터 생성을 요청한다.
8.  View는 응답 데이터를 생성하여 다시 DispatcherServlet에게 전달한다.  
    DispatcherServlet은 View로부터 전달받은 응답 데이터를 최종적으로 클라이언트에게 전달한다.

## **Reference**

-   이미지1 - [https://terasolunaorg.github.io/](https://terasolunaorg.github.io/guideline/1.0.1.RELEASE/en/index.html)
