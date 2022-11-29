# **리액티브 시스템**

-   Reactive System
-   대략 "클라이언트의 요청에 반응을 잘하는 시스템" 의 의미
-   쓰레드의 Non-Blocking과 관련이 있음

![image](https://user-images.githubusercontent.com/110891599/204559100-694286ab-aa4d-4611-97db-4345f19f0da8.png)


-   리액티브 시스템의 특징 설명
    -   MEANS  
        리액티브 시스템에서 사용하는 커뮤니케이션 수단
        -   Message Driven  
            리액티브 시스템에서는 메시지 기반 통신을 통해 여러 시스템 간 느슨한 결합을 유지함
    -   FORM  
        FORM은 메시지 기반 통신을 통해 리액티브 시스템이 어떤 특성을 가지는 구조로 형성되는지를 의미함
        -   Elastic  
            시스템으로 들어오는 요청량이 적든 많든 상관없이, 일정한 응답성을 유지하는 특징
        -   Resilient  
            시스템의 일부분에 장애가 발생하더라도 응답성을 유지하는 특징
    -   VALUE  
        리액티브 시스템의 핵심 가치
        -   Responsive  
            리액티브 시스템은 클라이언트의 요청에 즉각적으로 응답할 수 있어야 함
        -   Maintainable  
            클라이언트의 요청에 대한 즉각적인 응답이 지속가능해야 함
        -   Extensible  
            클라이언트의 요청에 대한 처리량을 자동으로 확장/축소 가능해야 함

# **리액티브 프로그래밍**

-   리액티브 시스템에서 사용되는 프로그래밍 모델
-   리액티브 프로그래밍 = Non-Blocking 통신을 위한 프로그래밍 모델

> **Reactive Programming의 특징  
> In computing, reactive programming is a declarative programming paradigm concerned with data streams and the propagation of change. With this paradigm, it's possible to express static (e.g., arrays) or dynamic (e.g., event emitters) data streams with ease, and also communicate that an inferred dependency within the associated execution model exists, which facilitates the automatic propagation of the changed data flow.**

# **리액티브 스트림즈(Reactive Streams)**

-   JPA, JDBC 가 ORM 기술 또는 DB에 액세스 하기위한 표준 사양(Spec)인 것처럼,  
    리액티브 스트림즈는 리액티브 프로그래밍을 위한 표준 사양임
-   다른 스펙들처럼, 리액티브 스트림즈도 인터페이스로 정의되어있다.
    -   리액티브 스트림즈의 컴포넌트
        -   Publisher : 데이터 소스로부터 데이터를 emit하는(내보내는) 역할
        -   Subscriber : Publisher가 내보낸 데이터를 소비하는 역할
        -   Subscription : Subscriber의 구독 자체를 표현한 인터베이스
        -   Processor : Publisher와 Subscriber를 상속하는 인터페이스. 두 가지 역할을 동시에 할 수 있음
-   리액티브 스트림즈의 구현체
    -   Project Reactor
        -   Reactor는 대표적인 구현체로써 Spring과 궁합이 가장 잘 맞는 리액티브 스트림즈 구현체임
        -   Reactor는 Spring 5의 리액티브 스택에 포함되어 있으며, Spring Reactive Application 구현에 있어 핵심적인 역할을 담당함
    -   RxJava
        -   RxJava는 .NET 기반의 리액티브 라이브러리를 넷플릭스에서 Java언어로 포팅한 JVM 기반의 리액티브 확장 라이브러리임
        -   RxJava의 경우 2.0부터 리액티브 스트림즤 표준 사양을 준수하며, 이전 버전의 컴포넌트와 함께 혼용되어 사용되고 있음
    -   Java Flow API
        -   Java 9부터 지원되는 Java 자체 리액티브 스트림즈 API
        -   다만, Flow API는 리액티브 스트림즈를 구현한 구현체가 아니라, 리액티브 스트림즈 표준 사양을 Java 안에 포함시킨 구조라고 볼 수 있음
        -   다양한 벤더들이 JDBC API를 구현한 드라이버를 제공할 수 있도록 SPI(Service Provider Interface) 역할을 하는 것 처럼, Flow API도 리액티브 스트림즈 사양을 구현한 여러 구현체들에 대한 SPI 역할을 한다고 보면 됨

# **Reference**

-   그림 1 - [https://www.reactivemanifesto.org/](https://www.reactivemanifesto.org/)
