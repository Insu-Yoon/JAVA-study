# **Spring Security**

-   Sping MVC 기반 애플리케이션의 인증(Authentication)과 인가(Authorization) 기능을 지원하는 프레임 워크
-   Spring Security를 통해 구현할 수 있는 보안 강화 기능
    -   Form Login, Token 기반 인증, OAuth2 기반 인증, LDAP 인증 등의 사용자 인증 기능 적용
    -   앱 사용자의 역할(Role)에 따른 권한 레벨 적용
    -   앱에서 제공하는 리소스에 대한 접근 제어
    -   민감한 정보에 대한 데이터 암호화
    -   SSL 적용
    -   일반적인 웹 보안 공격 차단
-   용어 정리
    -   Principal(주체)
        -   애플리케이션에서 작업을 수행할 수 있는 사용자, 디바이스, 시스템 등
        -   일반적으로 인증 프로세스가 성공적으로 수행된 사용자의 계정 정보를 의미함
    -   Authentication(인증)
        -   앱을 사용하는 사용자가 본인임을 증명하는 절차
        -   Authentication을 수행하기 위해서는 사용자를 식별하기 위한 정보가 필요한데, 이를 Credential(신원 증명 정보)라고 한다.
        -   로그인 시 입력하는 비밀번호도 Credential이 된다.
    -   Authorization(인가)
        -   인증이 정상적으로 수행된 사용자에게 하나 이상의 권한을 부여하는 것
        -   인가는 반드시 인증 이후에 수행된다.
        -   권한은 일반적으로 역할(Role)의 형태로 부여된다.
    -   Access Control(접근 제어)
        -   사용자가 앱의 리소스에 접근하는 행위를 제어하는 것
        -   권한에 따라 접근에 대한 제어가 달라질 것이다.

---

# **코드로 작성해보기**

> **https://github.com/Insu-Yoon/be-template-hello-spring-security**  
> // private repo

# **요약**

-   Spring Security를 이용한 보안 설정은 HttpSecurity를 파라미터로 가지고, SecurityFilterChain을 리턴하는 Bean을 생성하여 구성된다.
-   HttpSecurity를 통해 Spring Security에서 지원하는 보안 설정을 구성할 수 있다.
-   로컬 환경에서 Spring Security를 테스트하기 위해서는, CSRF 설정을 disable 해줘야 한다.
-   InMemoryUserDetailsManager 를 이용해 DB 연동 없이 테스트 목적의 InMemoryUser를 생성할 수 있다.
    -   InMemory -- 는 앱 실행 종료 시 삭제된다
-   Spring Security는 사용자의 Credential을 암호화 하기위한 PasswordEncoder를 제공하며, 이는 다양한 암호화 방식을 제공한다. Spring Security에서 지원하는 PasswordEncoder의 기본 암호화 알고리즘은 bcrypt이다.
-   비밀번호같은 민감한 정보는 반드시 암호화되어 저장되어야 한다.
    -   비밀번호 같은 경우 복호화 할 이유가 없기 때문에, 단방향 암호화 방식으로 암호화되어야 한다.
-   Spring Security에서 SimpleGrantedAuthority를 사용해 Role 형태의 권한을 지정할 때는 "ROLE\_" + 권한명 형태로 지정해야 한다.
    -   SecurityConfiguration 클래스에서 권한 인가시엔 아래처럼 설정("ROLE\_" 없이, hasRole("역할명"))

```java
.authorizeHttpRequests(authorize -> authorize 
                        .antMatchers("/orders/**").hasRole("ADMIN")
                        .antMatchers("/members/my-page").hasRole("USER")
                        .antMatchers("/**").permitAll()
```

-   Spring Security에서 관리하는 User 정보는 UserDetails 로 관리
    -   UserDetails는 UsderDetailsService에 의해 로드되는 핵심 User 정보를 표현하는 인터페이스
    -   인증을 시도하는 주체를 User라고 부른다. Principal은 User의 더 구체적인 정보를 의미하며, 일반적으로 Username이 principal이라고 생각하면 될 듯 하다.
    -   AuthenticationProvide는 Spring Security에서 클라이언트로부터 전달받은 인증 정보를 바탕으로 '인증된 사용자인가?'에 대해 처리하는 컴포넌트이다.
