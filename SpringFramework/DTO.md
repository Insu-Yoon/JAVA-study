> **참고) 주로 다루게 될 JSON 타입의 데이터가 입력된 경우에 초점을 맞춰 작성함.** 
## **DTO**
-   데이터를 전송하기 위한 용도의 객체
-   클라이언트에서 서버로, 서버에서 클라이언트로 전송하는 요청 및 응답 데이터로 DTO 사용가능
### **DTO의 필요성**
-   **HTTP 요청의 수를 줄일 수 있다.**
    -   HTTP요청 수는 비용 문제(자원 소모)와 직결된다.
    -   하나의 객체로 요청 데이터를 받아오므로, 효율성이 증가한다.
-   코드가 간결해진다  
    -   @RequestParam을 통해 파라미터를 하나하나 받아오면, 받아오는 항목이 많을수록 코드가 지저분해진다.
    -   DTO는 객체 하나에 요청 데이터를 담아서 받기 때문에, 코드가 간결해진다.
-   데이터 유효성 검증(Validation)의 단순화
    -   DTO를 사용할 경우, 유효성 검증을 별도의 클래스에서 구현하므로 SRP, AOP 적인 관점에서 좋은 객체 지향 설계 방식이 된다.
코드를 통해 DTO를 적용하고 살펴보자.
```java
//@RequestParam 애너테이션을 통해 데이터를 하나씩 받아오는 코드
@PostMapping
public ResponseEntity postMember(@RequestParam("email") String email,
                                 @RequestParam("name") String name,
                                 @RequestParam("phone") String phone) {...}
```

```java
//DTO 클래스를 만들고, 해당 클래스의 객체로 요청 데이터를 받아오는 코드
@PostMapping
public ResponseEntity postMember(@RequestBody PostMemberDto postMemberDto) {...}
```

위와 같이 email, name, phone의 값을 가진 JSON 데이터를 받아오는 DTO 클래스의 형태는 아래와 같다.

```java
public class PostMemberDto {
    
    private String email;
    private String name;
    private String phone;
​
    public String getEmail() {return email;}
​
    public void setEmail(String email) {this.email = email;}
​
    public String getName() {return name;}
​
    public void setName(String name) {this.name = name;}
​
    public String getPhone() {return phone;}
​
    public void setPhone(String phone) {this.phone = phone;} 
}
```

단순히 요청으로 받을 데이터들을 필드로 갖고, getter/setter 메서드만 가지고 있는 모습이다.

> **@RequestBody : JSON 형식의 Request Body를 DTO 클래스의 객체로 변환시켜주는 역할**  
> **@ResponseBody : DTO 클래스의 객체를 JSON 타입의 Response Body로 변환하는 역할**  
> **\- @ResponseBody 애너테이션의 경우, ResponseEntity 객체를 리턴 값으로 사용 할 경우 생략할 수 있다.**


> **JSON 역직렬화(Deserialization) : JSON 형식의 데이터를 DTO 같은 Java의 객체로 변환하는 과정**  
> **JSON 직렬화(Serialization) : Java의 객체를 JSON 형식의 데이터로 변환하는 과정**



> **DTO 클래스의 일반적인 형태를 보여주기 위해 Setter 메서드까지 작성했지만, @PostMapping의 경우 ObjectMapper가 리플렉션 API를 사용해 필드에 접근해서 매핑하기에, Setter 메서드가 불필요하다.**
​
### **DTO의 단점**
​
-   POST, GET, PUT 등등 하나의 요청에 대해 하나의 DTO를 작성해야 한다.
    -   공통적인 부분을 묶어 관리할 수도 있으나, 이 경우 유효성 검증 측면에서 오히려 비효율적일 수 있다.
​
## **DTO 유효성 검증(Validation)**
​
-   입력되는 요청 사항이 유효한 지 검증하는 과정
-   프론트 엔드 쪽에서도 유효성 검증을 하지만, 검증 후 필요에 따라 데이터를 가공 후 백엔드 측으로 넘길 수 있으므로, 백엔드에서도 유효성 검증을 반드시 해야한다.
​
### **DTO 클래스에 유효성 검증 적용**
​
> **우선 유효성 검증을 적용하기 위해서는 build.gradle 파일의 dependencies 항목에 'org.springframework.boot:spring-boot-starter-validation'을 추가해야 한다.  
> (Spring Boot에서 지원하는 Starter)**
​
위에서 코드로 작성해 본 PostMemberDto 클래스에서, 아래와 같은 제약 사항을 가정하여 유효성 검증을 구현해보자.
​
-   email
    -   값이 비어있지 않거나, 공백이 아니어야 한다.
    -   유효한 이메일 주소 형식이어야 한다.
-   name
    -   값이 비어있지 않거나, 공백이 아니어야 한다.
-   phone
    -   값이 비어있지 않거나, 공백이 아니어야 한다.
    -   010으로 시작하는 11자리 숫자와 '-'로 구성된 문자열이어야 한다.
        -   ex) 010-1234-5678
​
구현한 코드
​
```java
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
​
public class PostMemberDto {
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    private String name;
    
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$")
    private String phone;
​
    public String getEmail() {return email;}
​
    public void setEmail(String email) {this.email = email;}
​
    public String getName() {return name;}
​
    public void setName(String name) {this.name = name;}
​
    public String getPhone() {return phone;}
​
    public void setPhone(String phone) {this.phone = phone;} 
}
```
​
@NotBlank, @Email, @Pattern, regexp과 문자열이 보인다. 하나씩 짚어보자
​
-   @NotBlank 
    -   입력되는 데이터에서 이메일 정보가 비어있지 않은지 검증한다.
    -   null 값이나 빈 문자열(""), 공백(" ") 을 모두 허용하지 않는다.
    -   유효성 검증에 실패하면 에러 메세지가 콘솔에 출력된다.
        -   @NotBlank(message = "커스텀 에러 메세지") 를 통해 출력될 에러 메세지를 설정할 수 있다.
-   @Email
    -   유효한 이메일 주소인지 검증한다.
        -   abc123@hello 같은, 최상위 도메인이 없는 case도 유효성 검사를 통과하는 경우가 있다. 이에 대해 더 자세하고 정확한 유효성 검증을 실행하고 싶다면, 정규 표현식을 이용하여 세밀한 유효성 검사 조건을 지정할 수 있다.
    -   유효성 검증에 실패하면 내장된 디폴트 에러 메세지가 콘솔에 출력된다.
-   @Pattern
    -   입력된 데이터가 정규표현식(Regular Expression)에 Match되는 유효한 데이터인지 검증한다.
    -   유효성 검증에 실패하면 내장된 디폴트 에러 메세지가 콘솔에 출력된다.
​
> **유효성 검증 핵심 요약**  
> **\- 프런트엔드에서 유효성 검증을 진행했더라도, 서버쪽에서 추가적으로 유효성 검증을 실행해야 한다.**  
> **\- Jakarta Bean Validation의 애너테이션(@NotBlank, @Min, @Max, @Negative 등등)을 사용하면, Controller 로직에서 유효성 검증 로직을 분리할 수 있다.**  
> **\- Spring에서 지원하는 @Validated 애너테이션을 사용하면 쿼리 파라미터 및 @Pathvariable에 대한 유효성 검증을 실행할 수 있다.**  
> **\- Jakarta Bean Validation에서 Built-in으로 지원하지 않는 애너테이션은 Custom Validation을 통해 직접 Custom Annotation을 구현한 후 적용할 수 있다.**
​
## **정규표현식(Regular Expression)**
> **문자열을 처리하는 방법 중의 하나로, 특정한 조건의 문자를 검색, 추출, 치환하는 과정을 간편하게 처리할 수 있도록 하는 수단**

자세한 사항은 아래 링크 참조   
​
[https://www.w3schools.com/java/java\_regex.asp](https://www.w3schools.com/java/java_regex.asp)
​
[https://developer.mozilla.org/ko/docs/Web/JavaScript/Guide/Regular\_Expressions](https://developer.mozilla.org/ko/docs/Web/JavaScript/Guide/Regular_Expressions)
