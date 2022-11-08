# **트랜잭션**

Database에 대해 학습할 때 다뤄본 개념이다. 간단히 이야기하면 "DB의 무결성을 달성하기 위한 하나의 방법"이라고 생각할 수 있다.

# **ACID 원칙**

-   원자성(Atomicity)
    -   작업을 더이상 쪼갤 수 없음
    -   즉, 해당 작업의 내용 중 하나라도 실패한다면 전체가 실패한 것으로 간주하고 롤백해야 한다.
-   일관성(Consistency)
    -   비즈니스 로직에서 의도하는대로 일관성 있게 저장/변경 되는 것
    -   ex) 기존 Member 테이블에 NAME 컬럼이 존재하고, not blank라면 신규 회원 또한 반드시 이름을 가져야하고, 특정 회원을 update하더라도 이름이 사라져서는 안된다.(이름이 없는 회원이 존재해서는 안된다)
-   격리성(Isolation)  
    -   여러개의 트랜잭션이 실행될 경우 독립적이다. 다른 트랜잭션에 영향 x
-   지속성(Durabilty)
    -   트랜잭션의 결과가 저장되어야 한다.
    -   기록이 남아야 한다고 생각하자.

# **트랜잭션의 구현**

## **클래스 레벨에서의 적용**

```java
//트랜잭션을 사용하기 위해 import
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional   // @Transactional 애너테이션을 통해 트랜잭션 적용
public class MemberService {...}
```

위와 같이 클래스에서 @Transactional 애너테이션을 통해 트랜잭션을 적용할 수 있다.

## **메서드 레벨에서의 적용**

```java
@Transactional(readOnly = true)
public Member findMember(long memberId) {
	return findVerifiedMember(memberId);
}
```

마찬가지로 메서드에 @Transactional 애너테이션을 붙여 적용할 수 있다.

-   위와 같이 readOnly = true 속성이 붙을 경우, findMember 메서드는 읽기 전용 트랜잭션이 적용된다.
-   읽기 전용 트랜잭션의 경우, persistence context에서 flush가 호출되지 않으며, 스냅샷 생성도 진행하지 않는다.
-   따라서 불필요한 추가 동작이 줄어들고, 결과적으로 성능적인 이득을 볼 수 있다.

## **트랜잭션 적용 순서**

클래스와 메서드에 둘 다 @Transactional 애너테이션이 붙으면 우선 메서드 레벨의 트랜잭션이 적용되고, 만약 메서드 레벨에서 트랜잭션이 적용되지 않았을 경우 클래스 레벨의 트랜잭션이 적용된다.
