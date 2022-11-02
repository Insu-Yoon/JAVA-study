> **큰 흐름**  
> **"엔티티와 테이블을 매핑"**  
> **"테이블에 Id와 필드를 각각의 컬럼에 매핑"**

# **엔티티 - 테이블 매핑**

```java
@Entity
@Table
public class Member {
    @Id
    private Long memberId;
}
```

-   @Entity : 해당 클래스가 엔티티임을 알림
    -   (name = "엔티티명") 을 통해, 엔티티 명을 지정할 수 있다.
    -   name 애트리뷰트를 설정하지 않으면, 기본값으로 클래스의 이름을 엔티티명으로 사용한다.
-   @Table : 옵셔널 애너테이션. 안붙여도 어차피 Table로 작성한다.
    -   Table(name = "테이블명") 으로 테이블 명을 따로 지정할 경우에 사용한다고 생각하자.

> **@Id 애너테이션  
> 기본적으로 JPA에서 정의한 javax.persistence의 @Id를 사용한다.  
> **org.springframework.data.annotation에서 제공하는 @Id 애너테이션도 존재한다.  
> 해당 애너테이션은 persistence API가 없는 비관계형 DB(MongoDB 등)를 지원하기 위해 사용된다.

# **기본키 매핑**

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long memberId;
```

-   @Id : 이 변수는 식별자입니다. 라고 선언
-   @GeneratedValue : 기본키 자동 생성
    -   strategy엔 세 가지 종류가 있다.
        -   IDENTITY : mySQL 에서 주로 사용
        -   SEQUENCE : Oracle에서 주로 사용
        -   AUTO : JPA가 데이터베이스의 Dialect에 따라 적절한 전략을 자동으로 선택

# **필드와 컬럼 간의 매핑**

```java
@Column(length = 50, nullable = false, updatable = false, unique = true)
private String email;

public Member(String email){
	this.email = email;
}
```

-   @Column : 옵셔널 애너테이션. 안붙여도 JPA가 컬럼과 매핑되는 필드라고 간주하고 매핑한다.
    -   단, 애너테이션 자체를 생략하거나 애트리뷰트 없이 @Column만 입력 시, 모든 애트리뷰트가 default 값으로 지정된다.
    -   name : 컬럼명 지정
    -   length : 최대 길이 제한
    -   nullable : null 값을 허용할 지 여부
        -   default : true
    -   updatable : 수정 가능한지 여부
        -   default : true
        -   실제 동작 시, updatable = false면 EntityManager가 SQL문은 생성하지만, 전송하지 않는 방식으로 동작한다.
    -   unique : 해당 필드(컬럼)이 고유한 값인지에 대한 여부
        -   default : false

> **기억해둘 부분**  
> nullable은 default값이 true이다.  
> 만약 변수가 int, long 등의 primitive 타입인 경우, null을 입력할 수 없다.  
> 따라서 따로 설정하지 않는다면 에러를 만날 위험이 상당히 커진다.  
> **\-> 최소한 nullable = false 정도는 설정해두는 것이 유용하다.**

-   @Transient : 해당 변수를 테이블의 컬럼과 매핑하지 않겠다는 의미
