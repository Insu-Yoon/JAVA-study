# **JPA**

> **Java 진영에서 사용하는 ORM(Object-Relational Mapping) 기술의 표준 사양**  
> 표준 사양이다 = JPA는 인터페이스로 정의되어 있다 = JPA의 구현체가 따로 존재한다.  
> 구현체는 대표적으로 Hibernate ORM, EclipseLink, DataNucleus 등 존재

## **데이터 액세스 계층에서 JPA의 위치**

> **JPA - Hibernate ORM(JPA의 구현체) - JDBC API - DB**

-   CRUD 작업은 JPA를 거쳐 구현체인 Hibernate ORM에서 이루어진다.
    -   Hibernate ORM 내부에서 JDBC API를 이용해 DB에 접근한다.'
    -   remind) ORM : 객체와 DB 테이블의 매핑을 통해 Entity클래스 객체에 포함된 정보를 테이블에 저장하는 기술

> **REMIND** 
> **객체 지향 관점에서 생각하자**
> \-  추상화된 인터페이스(JPA)에만 의존하여 DB에 접근한다면 내부적으로 무슨일이 일어나는지는 알 필요가 없다.  
>    (설계 관점에서 그렇다는 것이지, 개발자로서는 내부적인 프로세스도 아는 것이 당연히 더 좋을 것이다.)  
> \-  따라서 JPA에서 지원하는 API를 사용해 DB에 접근하는 방법만 학습하면 된다.  
>    (구현체에서 별도로 지원하는 API를 사용한다면, 당연히 해당 부분에 대한 학습은 필요할 것이다.)  
>   
> \-  **생각해볼 부분) 구현체가 별도로 지원하는 API를 사용하게 되면, 다른 구현체를 사용하게 될 경우 SOLID의 OCP 원칙이 무너지고, 소스 코드에서 적든 많든 변경이 필요해진다.  -> 구현체의 별도 API는 사용을 최소화 하는 것이 좋을까?**

## **영속성 컨텍스트(Persistence Context)**

> **JPA에서 Persistence의 의미 - "오래 지속되게 한다."**

-   JPA는 테이블과 매핑되는 Entity 객체 정보를 영속성 컨텍스트에 보관하여, 애플리케이션 내에서 오래 지속되게 한다.
    -   이렇게 저장된 Entity 정보는 DB 테이블에 데이터를 CRUD하는데 사용된다.
    -   Persistence Context는 내부적으로 다음과 같은 구조
        -   1차 캐시
            -   em.persist(); 실행시 1차 캐시에 entity정보가, 쓰기 지연 SQL 저장소에 SQL문이 등록됨
        -   쓰기 지연 SQL 저장소
            -   트랜잭션 객체(tx)에 의해 flush();가 실행되면 DB에 SQL문 전송
            -   commit(); 을 실행하면 내부적으로 flush() 메서드가 실행된다.

## **사용되는 API**

-   EntityManagerFactory : EntityManager를 관리하는 API 
    -   createEntityManager() 메서드를 통해 EntityManager 생성

```java
@Bean
public CommandLineRunner testJpaBasicRunner(EntityManagerFactory emFactory) {
        this.em = emFactory.createEntityManager();
        ...
}
```

-   EntityManager : Entity를 관리하는 API  
    -   em.persist(entity) : 엔티티를 persistence context에 저장
    -   em.find(Entity.class, Id) : 엔티티 클래스와 Id를 지정하여 검색
        -   persist()를 통해 persistence context에 저장되어 있으면 해당 컨테이너 안에서 검색
        -   만약 persistence context에 해당 정보가 없으면 DB에 SELECT 쿼리문 전송

-   EntityTransaction  
    -   특정 Entity Manager를 통해 Transaction 객체 획득
        -   tx = em.getTransaction(); 
        -   JPA에서는 tx와 같은 Transaction 객체를 기준으로 DB의 테이블에 데이터를 저장한다.
    -   tx.begin() : 트랜잭션 시작
    -   tx.commit() : 쿼리문을 커밋한다. 데이터는 DB 테이블에 등록되고, 쓰기 지연 SQL 저장소의 쿼리문은 전송되고 사라진다.

# **CRUD 구현**

## **기본 설정**

```java
@Configuration
public class JpaConfig{	

    private EntityManager em;
    private EntityTransaction tx;


    @Bean
    public CommandLineRunner testJpaBasicRunner(EntityManagerFactory emFactory) {
        this.em = emFactory.createEntityManager();
        this.tx = em.getTransaction();

        return args -> {
        	//여기서 CRUD 메서드 호출
        };
    }
    
    //CRUD 메서드 정의
}
```

## **Create**

```java
private void create(){
	Member member = new Member();
    em.persist(member);
	tx.begin();
    tx.commit();
}
```

-   em.persist(member)가 tx.begin() 이후에 호출되어야할 것 같지만, tx.commit(); 을 실행하는 시점에 persistence context에 저장된 엔티티 정보를 DB에 반영하기 때문에, tx.begin()과 em.persist() 의 순서는 위의 코드처럼 되어있어도 무방하다.
    -   하지만 트랜잭션 내부에서 별도의 처리가 이루어질 수 있으므로 tx.begin()을 먼저 호출하는 게 더 좋을 것 같다.

### **Read**

```java
private void read(){
	System.out.println(em.find(Member.Class, 1L));
}
```

-   persistence context에 해당 객체가 이미 저장되어 있다면, 테이블에서 조회하는 것이 아니라 persistence context에서 해당 객체를 조회한다.  
    -   만약 다른 EntityManager가 테이블에 접근해 조회하려는 데이터를 변경했다면?
        -   아마 그걸 방지하기 위해 트랜잭션이 필요할 것이다.
        -   따라서 위의 Create 항목에서 짚었듯, tx.begin()으로 트랜잭션을 먼저 실행하는 것이 옳을 것 같다.

## **Update**

```java
private void update(){
        tx.begin();
        em.persist(new Member("Ingsu.gmail.com"));
        tx.commit();

        tx.begin();
        Member member1 = em.find(Member.class, 1L);
        member1.setEmail("Ingsu2@gmail.com");
        tx.commit();
}
```

-   read() 메서드처럼 persistence를 먼저 검색하고, 원하는 객체가 없으면 DB에 SELECT 쿼리를 전송한다.
-   해당 객체를 setter를 통해 수정한 후, tx.commit()을 호출하여 DB 테이블에 등록한다.
-   em.update() 같은 API가 존재할 것 같지만 setter만으로 업데이트 로직은 완성된다.
-   실제로 위의 코드를 실행하면 UPDATE 쿼리가 전송된다.

## **Delete**

```java
private void delete() {
        tx.begin();
        em.persist(new Member("Ingsu@gmail.com"));  
        tx.commit();   

        tx.begin();
        Member member = em.find(Member.class, 1L);
        em.remove(member);
        tx.commit();     
}
```

-   원하는 객체를 검색한 다음, em.remove()를 통해 삭제한다.
-   tx.commit()을 통해 DB에 해당 사항을 적용한다.
-   위의 코드를 실행하면 DELETE 쿼리를 전송한다.

# **핵심 요약**

-   JPA는 Java 진영에서 사용하는 ORM 기술의 표준 사양이다.
-   Hibernate ORM은 JPA에서 정의해 둔 인터페이스를 구현한 구현체이며, JPA가 지원하는 기능 외에도 Hibernate 자체적으로 사용 가능한 API를 지원한다.
-   JPA에서는 테이블과 매핑되는 엔티티 객체 정보를 영속성 컨텍스트(Persistence Context)에 보관하여, 애플리케이션 내에서 오래 지속되도록 한다.
-   Persistence Context 관련 API
    -   em.persist()
    -   em.find()
    -   setter를 통해 update
    -   em.remove()
    -   em.flush()
    -   tx.begin(), tx.commit()
