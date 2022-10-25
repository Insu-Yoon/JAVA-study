> Java에서 try-catch문을 이용한 예외 처리에 대해 살펴본 적이 있다.  
> **특정 예외가 발생했을 때의 동작을 설정함으로써 원하는 결과물을 만들어 낼 수 있을 것이다.**  
> **스프링에서도 @RestControllerAdvice, @ExceptionHandler를 이용해 예외처리를 할 수 있다.**

# **예외 처리의 필요성**

먼저 @ExceptionHandler 애너테이션을 이용한 예외 처리에 대해 살펴보자.

[##_Image|kage@bp0tCz/btrPzj3fLaf/EbABJ8GjSOqUy4VdZDsUMK/img.png|CDM|1.3|{"originWidth":334,"originHeight":83,"style":"alignCenter","width":450,"height":112,"caption":"잘못된 요청에 대한 응답. 무엇이 잘못 되었는지 알 수 없다."}_##]

예외에 대한 별도의 동작을 설정하지 않을 경우 위의 사진과 같이 단순히 '잘못된 요청'이라는 응답만 클라이언트에 돌려준다. 요청 데이터가 왜 유효성 검증을 통과하지 못했는 지 알 수 없는 것이다.

@ExceptionHandler를 통해 예외에 대한 별도의 처리를 정의함으로써, 더 구체적인 응답을 제공할 수 있다.

# **Controller 레벨에서의 예외처리**

```java
@ExceptionHandler
public ResponseEntity handleException(MethodArgumentNotValidException e){
	final List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
	return new ResponseEntity<>(fieldErrors, HttpStatus.BAD_REQUEST);
}
```

MemberController 내에 위와 같은 메서드를 정의함으로써, Request Body에 포함된 요청이 올바르지 않을 경우에 대한 응답 메세지를 구현할 수 있다.

-   하지만 여전히 아래의 파라미터들을 모두 응답으로 돌려주기 때문에, 직관적이지 않다.
    -   objectName - 영향을 받는 객체의 이름
    -   field - 객체의 영향을 받는 필드
    -   rejectedValue - 거부된 필드 값
    -   bindingFailure - 오류가 바인딩 오류를 나타내는지 여부. else 유효성 검증 실패
    -   codes - 이 메세지를 해결하는 데 사용할 코드
    -   arguments - 이 메시지를 확인하는 데 사용할 인수 배열
    -   defaultMessage - 이 메시지를 확인하는 데 사용되는 기본 메시지

> **별도의 Error Response 클래스를 만들어, 필요한 정보만 담은 후 클라이언트에게 전달하자**

```java
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private List<FieldError> fieldErrors;

    @Getter
    @AllArgsConstructor
    public static class FieldError {
        private String field;
        private Object rejectedValue;
        private String reason;
    }
}
```

ErrorResponse의 List형 필드변수 fieldErrors에 FieldError 타입의 객체를 담고, 이를 응답으로 전송하는 코드이다.

이제 컨트롤러에서, 위의 클래스를 사용해 응답을 리턴하도록 수정해야 한다.

```java
@ExceptionHandler
public ResponseEntity handleException(MethodArgumentNotValidException e){
	final List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
    List<ErrorResponse.FieldError> errors = 
    		fieldErrors.stream()
            .map(error -> new ErrorResponse.FieldError(
            	error.getField(),
                error.getRejectedValue(),
                error.getDefaultMessage()))
            .collect(Collectors.toList());
	return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
}
```

ErrorResponse클래스에서 FieldError 클래스를 이용해 특정 정보들만 골라서 담고, List로 변환해 ResponseEntity에 담아서 반환한다.

이제 원하는 정보(field, rejectedValue, defaultMessage)만 골라 클라이언트에게 응답으로 돌려줄 수 있다.

# **위와 같은 방법의 단점**

-   각각의 Controller 클래스에서 따로따로 @ExceptionHandler를 사용하여 예외 처리를 해야한다
    -   코드의 중복이 발생한다.
-   Controller에서 발생하는 예외는 유효성 검증 실패만 있는 것이 아니므로, 하나의 Controller클래스 내에서 @ExceptionHandler를 추가한 메서드가 늘어난다
    -   가독성, 유지보수 down
-   DTO도 다른 클래스에게 "해 줘"하고 넘기는데, 예외 처리도 굳이 Controller가 붙잡고 있어야 할 이유가 없다.
    -   별도의 클래스에게 예외 처리에 대한 책임을 넘기도록 하자.

# **@RestControllerAdvice**

-   @RestControllerAdvice를 적용한 클래스를 이용하면, 여러 개의 RestController 클래스에서 @ExceptionHandler가 적용된 메서드를 공유하여 사용할 수 있다.
    -   즉, 예외처리를 공통화 할 수 있다.

```java
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {...}
```

> 자세한 코드는 GitHub에 private으로 정리해둠. 추후 필요하면 확인
