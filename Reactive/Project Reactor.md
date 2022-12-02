> Spring Reactive Web Application도 Spring MVC 기반의 애플리케이션과 요청처리 흐름이 거의 동일하다.  
> 다만 요청 처리 과정 중 Reactor가 핵심적인 역할을 한다는 점에서 다르다.

# **Project Reactor(Reactor)**

-   리액티브 스트림즈 표준 사양을 구현한 구현체 중 하나
-   Spring 5 버전부터 지원하는 리액티브 스택에 포함되어 있음
-   Spring Reactive Application 구현에 있어 핵심적인 역할을 담당하는 라이브러리

## **Reactor의 특징**

-   Non-Blocking 통신을 지원
    -   쓰레드가 차단되지 않는다는 의미
-   Publisher 타입으로 Mono\[0|1\]와 Flux\[N\] 이라는 두 가지 타입을 제공
    -   Mono는 0건 또는 1건의 데이터를 emit 할 수 있음
    -   Flux는 N건(여러 건)의 데이터를 emit 할 수 있음
-   MSA 구조에 적합한 라이브러리(Non-Blocking 통신을 지원하기 때문)
    -   MSA(Microservice Architecture) 기반의 앱들은 서비스 간의 통신이 잦기 때문에, Blocking 통신을 사용하기엔 무리가 있음
-   Backpressure 전략을 지원함  
    Backpressure는 Subscriber의 처리 속도가 Publisher의 emit 속도를 따라가지 못할 때 이를 적절하게 제어하는 전략

> **Publisher에서 데이터를 계속 emit하는데 Subscriber의 처리속도가 느리다면 , 처리되지 않고 대기하는 데이터가 지속적으로 쌓이면서 Overflow가 발생하며, 심할 경우 시스템이 다운될 수도 있다. 따라서 Backpressure 전략은 리액티브 프로그래밍에서 데이터를 적절하게 처리하기 위해 반드시 필요한 전략이다.**

-   -   DROP 전략
        -   버퍼가 가득 찬 상태라면, 이후에 emit된 데이터들은 모두 폐기(drop)하는 전략
    -   LATEST 전략
        -   버퍼가 가득 찬 상태라면, 가장 최근에(latest) emit된 데이터만 남기고, 나머지는 폐기하는 전략
    -   BUFFER DROP LATEST 전략
        -   버퍼가 가득 찰 경우, 버퍼 안에 있는 데이터 중 가장 최근에 버퍼에 채워진 데이터를 DROP하는 전략
    -   BUFFER DROP OLDEST 전략
        -   버퍼가 가득 찰 경우, 버퍼 안에 있는 데이터 중 가장 오래된 데이터를 DROP 하는 전략

---

# **마블 다이어그램(Marble Diagram)**

> **Marble = 구슬**  
> **구슬 모양의 동그라미는 하나의 데이터를 의미하며, 다이어그램 상에서 시간의 흐름에 따라 변화하는 데이터의 흐름을 표현함**

## **Mono의 마블 다이어그램**

![image](https://user-images.githubusercontent.com/110891599/205267672-91f027e0-b3ee-4c37-be46-ef4fcf0a083a.png)


-   위 아래의 타임라인(화살표)은 데이터가 흘러가는 시간의 흐름을 표현함
-   구슬 모양의 데이터가 하나 뿐 = Mono는 0건 또는 1건의 데이터만 emit하는 Reactor 타입이라서 이렇게 표현
-   상단의 타임라인에서 수직선은 Mono의 Sequence가 정상 종료됨을 의미
-   박스는 Mono에서 지원하는 Operator에서 입력으로 들어오는 구슬 모양의 데이터를 가공 처리하는 것을 표현
-   하단의 타임라인은 가공 처리된 데이터가 Downstream으로 전달될 때의 타임라인
-   Mono에서 emit된 데이터가 처리되는 과정에서 에러가 발생할 경우 하단 타임라인에서 보이듯 X로 표시

## **Flux의 마블 다이어그램**

![image](https://user-images.githubusercontent.com/110891599/205267696-8c215bbc-5bd8-4792-9e8a-687b4dff22f7.png)


-   Mono 마블 다이어그램과 다른 것은 emit된 데이터의 수 뿐이다.

---

# **스케쥴러(Scheduler)**

-   쓰레드를 관리하는 관리자의 역할 수행
    -   **Reactor의 Scheduler는 복잡한 멀티쓰레딩 프로세스를 단순하게 해줌**
    -   Reactor가 Non-Blocking 통신을 위한 **비동기 프로그래밍**을 위해 탄생했으므로, Scheduler는 Reactor에서 중요한 역할을 함

## **스케쥴러 전용 Operator**

-   subscribeOn()
    -   subscribeOn() 내부에 Schedulers.boundedElastic() 같은 스케쥴러를 지정하면, 구독 직후에 실행되는 쓰레드가  main 쓰레드(default 쓰레드)에서 지정한 쓰레드로 바뀜
    -   구독 시점 직후의 실행 흐름을 다른 스레드로 바꾸는 데 사용함
    -   주로 데이터 소스에서 데이터를 emit하는 원본 publisher의 실행 쓰레드를 지정하는 역할

> doOnSubscribe() 오퍼레이터  
> 구독 발생 직후에 트리거되는 오퍼레이터. 구독 직후에 어떤 동작을 수행하고 싶다면 해당 오퍼레이터 안에 로직을 작성하면 된다.

-   publishOn()
    -   publishOn(Schedulers.parallel())과 같이 쓰레드를 지정할 때 사용
    -   publishOn()을 기준으로 Downstream 쓰레드가 publishOn()에서 Scheduler로 지정한 쓰레드로 변경됨
    -   전달받은 데이터를 가공 처리하는 Operator 앞에 추가하여 실행 쓰레드를 별도로 추가하는 역할

---

# **Operators**

## **상황별로 분류된 Operator 목록**

-   새로운 Sequence를 생성(Create)하고자 할 경우
    -   just()
    -   ⭐ fromStream()
        -   Java의 Stream을 입력으로 전달 받아 emit함
    -   ⭐ fromIterable()
        -   Java의 Iterable을 입력으로 전달 받아 emit함
    -   fromArray()
    -   range()
    -   interval()
    -   empty()
    -   never()
    -   defer()
    -   using()
    -   generate()
    -   ⭐ create()
        -   프로그래밍 방식으로 Signal 이벤트를 발생시킴
        -   한번에 여러 건의 데이터를 비동기적으로 emit할 수 있음
-    기존 Sequence에서 변환 작업(Transforming)이 필요한 경우
    -   ⭐ map()
        -   가장 기본적인 가공 처리 오퍼레이터
    -   ⭐ flatMap()
        -   하나의 데이터가 들어올 때 마다 새로운 Sequence 생성  
            (2개의 데이터가 emit되고, flatMap() 내부에서 3개의 데이터를 emit하는 Sequence가 있다면 Downsstream으로 emit되는 데이터는 총 6개)
        -   flatMap() 내부에서 정의하는 Sequence는 Inner Sequence라고 부름
        -   내부에서 추가 쓰레드를 할당할 경우 작업의 처리 순서를 보장하지 않음
    -   ⭐ concat()
        -   입력으로 전달된 Publisher의 Sequence를 하나로 이어붙여 데이터를 순서대로 emit
    -   collectList()
    -   collectMap()
    -   merge()
    -   ⭐ zip()
        -   입력으로 전달되는 여러 개의 Publisher Sequence에서 emit된 데이터를 결합하는 Operator
        -   두 Publisher가 각각A,B,C,D 그리고 1,2,3,4 를 emit했다면, zip() 은 A와 1을, B와 2를, C와 3을, D와 4를 결합하여 Downstream으로 emit함
        -   만약 A,B,C 와 1,2,3,4,5 가 들어오면, A1 B2 C3로 결합시켜 내보내고, 4와 5는 폐기됨
    -   then()
    -   switchIfEmpty()
    -   and()
    -   when()

-   Sequence 내부의 동작을 확인(Peeking)하고자 할 경우
    -   doOnSubscribe
    -   ⭐doOnNext()
        -   데이터 emit 시 트리거링되어 side-effect를 추가할 수 있는 Operator
        -   리턴 값이 존재하지 않음
        -   주로 로깅에 사용, but 데이터를 emit하면서 필요한 추가 작업이 있다면 doOnNext()에서 처리할 수도 있음
    -   doOnError()
    -   doOnCancel()
    -   doFirst()
    -   doOnRequest()
    -   doOnTerminate()
    -   doAfterTerminate()
    -   doOnEach()
    -   doFinally()
    -   ⭐log()
        -   Publisher에서 발생하는 Signal 이벤트를 로그로 출력하는 역할
        -   오퍼레이터 여러개가 쭈르륵 있다? 한줄 한줄마다 로그 오퍼레이터 추가 가능

-   Sequence에서 데이터 필터링(Filtering)이 필요한 경우
    -   ⭐filter()
    -   ignoreElements()
    -   distinct()
    -   ⭐take()
        -   괄호 안에 지정한 숫자만큼의 데이터를 처리함
    -   next()
    -   skip()
    -   sample()
    -   single()

-   에러를 처리(Handling errors)하고자 할 경우
    -   ⭐error()
        -   Reactor Sequence 상에서 의도적으로 예외를 던짐(throw()와 동일)
        -   onError Signal 이벤트를 발생시키는 데 사용됨
    -   ⭐timeout()
        -   지정한 시간 동안 emit되는 데이터가 없으면, onError Signal 이벤트를 발생시킴
    -   onErrorReturn()
    -   onErrorResume()
    -   onErrorMap()
    -   doFinally()
    -   ⭐retry(n)
        -   Sequence 상에서 에러가 발생할 경우 n회 만큼 재구독하여 Sequence를 다시 시작함
