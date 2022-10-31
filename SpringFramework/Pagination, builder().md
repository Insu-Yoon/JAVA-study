# **Pageable**

```java
package org.springframework.data.domain;
```

-   스프링에서 지원하는 Pagination information을 위한 인터페이스
    -   지금 이해한 내용으로는, Pageable 객체를 통해 데이터를 받는 느낌
    -   PageRequest 클래스(결과적으로 Pageable의 구현 클래스)의 of() 메서드를 통해, 한 페이지에 담을 데이터의 수, 지금 가져올 페이지의 인덱스를 지정하는 것이 가능

```java
//params = int page, int size 일 때
//page = 1, size = 5를 입력 -> 첫번째 페이지(실제로는 0번 페이지)에 5개의 데이터를 담아 응답
//PageRequest.of(가져올 페이지, 페이지 당 데이터 수, 정렬 방법 정의)
Pageable pageable = PageRequest.of(page-1,size,Sort.by("memberId").descending());
```

# **Page**

```java
package org.springframework.data.domain;
```

-   스프링에서 지원하는 인터페이스
    -   지금 이해한 내용으로는, 일종의 Sublist
    -   Page<Member>와 같은 형태로 객체들을 담고있다

> **현재 이해한 Page, Pageable 흐름**

1.  Pageable이 한 페이지에 담을 데이터의 수와, 가져올 페이지를 입력받아 해당 정보를 담는다
2.  Pageable 객체를 통해 findAll(pageable)
3.  특정 페이지의 Member 데이터들이 리턴
4.  Page<Member> members로 받는다.

# **builder(), build()**

-   생성자, Setter, 정적 메서드 of() 처럼 객체를 생성하는데에 사용되는 방법 중 하나
-   장점
    -   필요한 데이터만 설정할 수 있다.
        -   따라서 유연성 확보
    -   가독성 확보
        -   어떤 필드를 채워 구성하는지 명확하다
    -   변경 가능성 최소화
        -   생성자의 경우, 일부의 필드만 받아올 경우 별도의 생성자가 필요하다

```java
 Member member = Member.build()
            .name("Ingsu")
            .blog("ingsu.tistory.com")
            .email("ingsu814@gmail.com")
            .build();
```
