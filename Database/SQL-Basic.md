> **데이터 베이스의 필요성**
>   
> 데이터를 처리하는 전통적인 방법에는 파일 입출력(File I/O)을 통해 엑셀 등의 스프레드 시트에 데이터를 저장하거나,  인메모리(In-memory) 형태로 데이터를 임시 저장하여 사용하는 방법이 있다.  
>   
> In-memory의 경우 프로그램이 종료될 경우 데이터가 사라지기 때문에, 예기치 못한 상황에서 데이터를 보호할 수 없으며 프로그램이 종료되면 데이터를 사용할 수 없다.  
>   
> File I/O의 경우 In-memory의 단점은 극복할 수 있으나 데이터를 처리할 때 마다 파일 전체를 읽어야 하므로 다루는 데이터가 커질수록 비효율적이고, 파일 여러개를 읽는 등 복잡한 데이터 처리 시 작업이 어려워진다는 문제가 있다.  
>   
> 이러한 배경 속에서 데이터 베이스가 고안되었다. 데이터 베이스는 데이터를 불러오기 쉽고, 대용량 데이터를 처리하기 용이하다.

# **SQL(Structured Query Language)**

SQL을 직역하면 구조화된 쿼리 언어가 될 것이다. 여기서 쿼리란 일종의 질의문을 지칭하며, 데이터베이스에 쿼리를 보내 원하는 데이터를 가져오거나 삽입할 수 있다.

SQL은 데이터 베이스, 그중에서도 관계형 데이터 베이스(RDB)에서 데이터를 관리 및 처리하기 위해 설계된 데이터 베이스 용 프로그래밍 언어다.

SQL은 데이터가 구조화된 테이블을 사용하는 데이터 베이스(ex. RDB)에서만 활용 가능하다. 

> 데이터가 구조화되지 않은 데이터 베이스를 NoSQL 데이터 베이스라고 부르며, NoSQL 데이터베이스의 대표적 예시로 문서형 데이터베이스인 MongoDB가 있다.

# **SQL 명령어**

-   CREATE - 데이터베이스 또는 테이블 생성

```sql
CREATE DATABASE 데이터베이스명
```

```sql
CREATE TABLE 회원(
    아이디 VARCHAR(16) NOT NULL,
    이름 VARCHAR(10) NOT NULL,
    나이 INT
);
```

-   USE - 데이터 베이스 접속

```sql
USE 데이터베이스명
```

-   DROP - 데이터베이스 또는 테이블 삭제

```sql
DROP DATABASE 데이터베이스명
```

```sql
DROP TABLE 회원
```

-   ALTER - 테이블 변경

```sql
ALTER TABLE 회원 ADD 전화번호 INT
//회원 테이블에 전화번호 COLUMN 추가
```

```sql
ALTER TABLE 회원 DROP 전화번호
//회원 테이블에서 전화번호 COLUMN 삭제
```

-   SELECT - 데이터 추출(가져오기)
-   FROM - 데이터를 추출할 테이블 명시
-   WHERE - 가져올 대상(조건) 지정
-   GROUP BY - 조건에 따라 그룹별로 검색
-   ORDER BY - 정렬
-   DISTINCT - unique한 값만 추출

```sql
SELECT COUNT(CustomerID),
Country
FROM Customers
GROUP BY Country
ORDER BY COUNT(CustomerID) DESC;
//각 국가의 고객 수를 고객이 많은 국가 순으로 나열
```

> w3schools의 SQL exercise - GROUP BY 의 2번에 나오는 예시이다.  
> 왜 SELECT COUNT(CustomerID) 로 끝나지않고 , Country가 붙는지는 잘 모르겠다. 해당 내용 추가적인 학습 필요

-   INSERT (+INTO + VALUE) - 테이블에 데이터 추가

```sql
INSERT
INTO 회원(아이디, 이름, 나이, 전화번호)
VALUE ('HGD', '홍길동', 20, 01000000000);
```

-   UPDATE(+ SET) - 데이터 수정

```sql
UPDATE 회원
SET 나이 = 21
WHERE 아이디 = 'HGD';
```

-   DELETE (+ FROM) - 데이터 삭제

```sql
DELETE
FROM 회원
WHERE 아이디 = 'HGD';
```
