# **Checked Exception & Unchecked Exception**

-   Checked Exception  
    -   **반드시 예외에 대한 처리를 해야 한다.**
    -   **컴파일 단계에서 예외를 확인한다.**
    -   예외 발생 시 트랜잭션을 롤백 하지 않는다.
    -   Exception의 하위 클래스
    -   예) FileNotFoundException, ClassNotFoundException 등
-   Unchecked Exception
    -   **예외에 대한 처리가 강제되진 않는다.**
    -   **run-time에 예외를 확인한다.**
    -   **예외 발생 시 트랜잭션을 롤백한다.**
    -   Exception의 하위 클래스인 RuntimeException의 하위 클래스
    -   예) NullPointException, IndexOutOfBoundException 등

> Unchecked Exception에 대해 예외 처리가 강제되지 않는 이유  
> \- Unchecked Exception은 개발자의 실수에 의해서도 발생한다(코드 상의 문제)  
> 따라서 만약 예외처리를 강제한다면 단순한 코드에도 일일이 try-catch문 등을 사용하여 예외처리를 해야 할 것이다.


> 기본적으로 예외 처리를 따로 구성한다면 Runtime 중 발생하는 Unchecked Exception에 대한 처리라고 받아들이면 되겠다.

# **의도적인 예외 발생시키기**

경우에 따라, 의도적으로 예외를 발생시키고 처리할 필요가 있을 수 있다. 아래 상황을 가정하자.

-   계좌와 연동하는 서비스에서, 잔액이 부족해 트랜잭션이 롤백된 상황
    -   클라이언트에게 현재 상황을 알릴 필요가 있다.
-   시스템 내부에 조회하려는 리소스가 없는 경우
    -   마찬가지로 해당 리소스가 존재하지 않는다는 응답을 보낼 필요가 있다.

위와 같은 상황에서 따로 예외를 발생시켜 처리하지 않는다면, 클라이언트 쪽에서는 '뭔가 실패했다' 라는 정보 외에는 알 방법이 없다.

이럴 때, throw를 통해 강제로 예외를 발생시키고 처리과정을 통해 피드백을 줄 수 있다.

# **Custom Exception**

RuntimeException 클래스를 확장하여, 사용자 정의 예외를 정의할 수도 있다.

```java
public class BusinessLogicException extends RuntimeException {
    @Getter
    private ExceptionCode exceptionCode;

    public BusinessLogicException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
```

위 코드의 경우, ExceptionCode 클래스를 따로 정의하여 예외를 처리하는 방법으로 구현되어 있다.

이를 이용해 아래와 같이 강제로 예외를 발생시킬 수 있다.

```java
public Member findMember(long memberId) {
		...
        throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
}
```

해당 예외에 대한 처리는 지난 포스트에서 만들어놓은 GlobalExceptionAdvice에서 담당하게 된다.

```java
@ExceptionHandler
public ResponseEntity handleBusinessLogicException(BusinessLogicException e) {
	ErrorResponse response = ErrorResponse.of(e);
	return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode().getStatus()));
}
```
