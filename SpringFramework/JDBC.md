# **JDBC(Java Database Connectivity)**

Java 기반 애플리케이션 코드 레벨에서 사용하는 데이터를 DB에 저장 또는 업데이트 하거나, DB에 저장된 데이터를 Java코드 레벨에서 사용할 수 있도록 해주는 스펙(Specification, 표준 사양)이다.

JDBC API를 통해 다양한 벤더(Oracle, MS SQL, MySQL 등)의 DB와 연동할 수 있다.

## **JDBC 동작 흐름**

> **Java 앱 - JDBC API - JDBC 드라이버 - 데이터베이스**

-   JDBC 드라이버
    -   DB와의 통신을 담당하는 인터페이스
    -   Oracle, MS SQL, MySQL 등의 벤더에서는 해당 벤더에 맞는 JDBC 드라이버를 구현해서 제공
    -   개발자는 JDBC 드라이버의 구현체를 이용하여 특정 벤더의 DB에 엑세스

## **JDBC API 사용 흐름**

> **JDBC 드라이버 로딩 - Connection 객체 생성 - Statement 객체 생성 - Query 실행 - ResultSet 객체로부터 데이터 조회 - ResultSet 객체 Close - Statement 객체 Close - Connection 객체 Close**

1.  JDBC 드라이버 로딩 : 사용하려는 JDBC 드라이버를 로딩한다.
    -   DriverManager라는 클래스를 통해 로딩된다.
2.  Connection 객체 생성
    -   DriverManager를 통해 DB와 연결되는 세션인 Connection 객체를 생성
3.  Statement 객체 생성
    -   Statement 객체는 작성된 SQL 쿼리문을 실행하기 위한 객체
    -   객체 생성 후 정적인 SQL 쿼리 문자열을 입력으로 가짐
4.  Query 실행
    -   생성된 Statement 객체를 이용해 입력한 SQL 쿼리를 실행
5.  ResultSet 객체로부터 데이터 조회
6.  ResultSet 객체, Statement 객체, Connection 객체 순서대로 Close
    -   JDBC API를 통해 사용된 객체들은 사용 후 사용한 순서의 역순으로 차례로 Close해야 한다.

## **Connection Pool**

> **JDBC API 사용 흐름 중 Connection 객체 생성은 Cost가 높은 작업 중 하나이다.**

그래서 애플리케이션 로딩 시점에서 Connection 객체를 미리 생성해두고, 필요할 때 미리 만들어둔 Connection 객체를 사용함으로써 애플리케이션의 성능을 향상시킬 수 있다.

> **Connection Pool  
>  - Connection을 미리 만들어서 보관하고, 필요할 때 Connection 객체를 제공하는 역할을 하는 관리자**

## **JDBC 핵심 요약**

-   JDBC는 Java 기반 애플리케이션의 코드 레벨에서 사용하는 데이터를 DB에 반영하거나, DB의 데이터를 Java 코드 레벨에서 사용할 수 있도록 해주는, Java에서 제공하는 표준 API 이다.
-   JDBC의 구체적인 API 사용법을 알 필요는 없다.(JDBC는 데이터 액세스 기술의 기본이 되는 Low level API이다. 따라서 개발자가 직접 사용하지 않고, Spring Data JDBC나 Spring Data JPA 등의 기술을 통해 더 편리하게 데이터 액세스 로직을 구현한다.
-   DB Connection 객체를 미리 만들어서 보관하고 애플리케이션이 필요로 할 때 Connection을 제공하는 역할을 하는 Connection 관리자를 Connection Pool이라고 한다.

# **Spring Data JDBC**

> **JPA처럼 ORM 기술을 사용하지만, JPA의 기술적 복잡도를 낮춘 기술**

- Spring Data JDBC 적용하기   
    1. build.gradle에 의존 라이브러리 추가
       - runtimeOnly 'com.h2database:h2'
       - implementation 'org.springframework.boot:spring-boot-starter-data-jdbc'
    1. application.yml 파일에 사용할 DB에 대한 설정을 작성
    1. schema.sql 파일에 필요한 테이블 스크립트 작성
    1. application.yml 파일에서 schema.sql 파일을 읽어 테이블을 생성할 수 있도록 초기화 설정 추가
    1. DB의 테이블과 매핑할 엔티티 클래스 작성
    1. 작성된 엔티티 클래스를 기반으로, DB의 작업을 처리할 Repository 인터페이스 작성
    1. 작성된 Repository 인터페이스를 서비스 클래스에서 사용할 수 있도록 의존성 주입
    1. 의존성 주입 후 서비스 클래스에서 Repository의 메서드를 사용해 DB에 접근, CRUD 작업 수행
