# **SQL 종류**

한국어나 영어 등 언어에서도 주어, 동사 같은 구분이 있듯, SQL에도 역할에 따라 문법이 존재한다.

일반적으로 SQL 문법은 아래와 같이 분류한다.

-   Data Definition Language(DDL)  
    -   데이터를 정의할 때 사용하는 언어
    -   CREATE, DROP 등이 DDL에 속함
-   Data Manipulation Language(DML)
    -   데이터베이스에 데이터를 저장할 때 사용하는 언어
    -   INSERT, DELETE, UPDATE가 DML에 속함
-   Data Control Language(DCL)
    -   데이터베이스에 대한 접근 권한과 관련된 것에 사용하는 언어
    -   GRANT(권한을 줌), REVOKE(권한을 가져감) 등이 DCL에 속함
-   Data Query Language(DQL)
    -   정해진 스키마 내에서 쿼리할 수 있는 언어
    -   SELECT가 DQL에 속함
    -   DQL은 DML의 일부로 취급하기도 한다.
-   Transaction Control Language(TCL)
    -   DML을 거친 데이터의 변경사항을 수정할 수 있다.
    -   COMMIT, ROLLBACK 등이 TCL에 속함
