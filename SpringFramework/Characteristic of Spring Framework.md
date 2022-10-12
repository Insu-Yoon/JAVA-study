# **POJO 프로그래밍을 지향한다**

POJO는 Plain Old Java Object의 약자이며, POJO가 의미하는 것은 'Java로 생성하는 순수한 객체'이다.

> **따로 용어를 만들어 '순수한 객체'라고 강조까지하는 POJO 프로그래밍이 의미하는 것이 무엇일까?**

우선 POJO 프로그래밍의 기본적 두 가지 규칙은 다음과 같다.

## **1\. Java 나 Java의 스펙(사양)에 정의된 것 이외의 다른 기술이나 규약에 얽매이지 않아야 한다.**

```java
public class MessageForm extends ActionForm{	
	String message;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}

public class MessageAction extends Action{	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
        throws Exception {
		MessageForm messageForm = (MessageForm) form;
		messageForm .setMessage("Hello World");
		return mapping.findForward("success");
	}
}
```

위 코드에서 MessageForm 클래스는 ActionForm을, MessageAction 클래스는 Action을 상속받고있다. 해당 클래스는 Struts라는 웹 프레임 워크에서 지원하는 클래스이다.

이런 식으로 특정 기술을 상속하여 코드를 작성하면, 나중에 애플리케이션의 요구사항이 변경되어 다른 기술로 변경할 때 Struts의 클래스를 명시적으로 사용했던 모든 부분을 일일이 찾아 수정하거나 제거해야한다.

또한 자바는 단일 상속을 원칙으로 하기 때문에 'extends' 키워드를 사용하여 한 번 상속을 하게되면 상위 클래스를 상속받아 하위 클래스를 확장하는 객체지향 설계 기법을 적용하는 것이 어려워진다.

## **2\. 특정 환경에 종속적이지 않아야 한다.**

특정 서블릿 컨테이너의 API를 직접 가져다가 사용한다고 가정하면, 시스템 요구 사항 변경 등의 이유로 해당 서블릿 컨테이너 대신 다른 서블릿 컨테이너를 사용하게 될 경우 끔찍한 일이 벌어진다.

> **코드에서 사용하고 있는 특정 서블릿 컨테이너의 API 코드를 모두 대체할 컨테이너의 API로 수정하든가, 최악의 경우 애플리케이션 자체를 뜯어고쳐야 될 수도 있다.**

POJO 프로그래밍의 원칙과 반대 예시를 통해  POJO 프로그래밍이 필요한 이유를 정리하면 다음과 같다.

-   특정 환경이나 기술에 종속적이지 않으면, 재사용 가능하며 확장 가능한 유연한 코드를 작성할 수 있다.
-   저수준 레벨의 기술과, 환경에 종속적인 코드를 앱 코드 내에서 제거함으로써 코드가 깔끔해진다.  
    코드가 깔끔해짐으로써 디버깅도 상대적으로 용이해진다.
-   특정 기술이나 환경에 종속적이지 않기 때문에 테스트도 단순해진다.
-   **객체지향적인 설계를 제한없이 적용할 수 있다. - 사실 이것이 핵심이다.**

# **Spring Framework와 POJO 프로그래밍**

그렇다면 Spring은 POJO 프로그래밍을 달성하기 위해 무엇을 지원할까?

-   IoC(Inversion of Control) / DI(Dependancy Injection) - 제어 역전 / 의존성 주입
-   AOP(Aspect Oriented Programming) - 관점 지향 프로그래밍
-   PSA(Portable Service Abstraction) - 일관된 서비스 추상화

하나씩 살펴보자

## **IoC/DI**

이전 글에서 Framework와 Library를 비교할 때 언급한 개념으로, 전통적인 방식과 다르게 애플리케이션 흐름의 주도권이 뒤바뀐 것을 IoC라고 한다. 이것을 실제로 구현하는 방법이 의존성 주입(DI)이다.

### **Java 콘솔 애플리케이션의 일반적인 제어권**

익히 공부한 Java 콘솔 애플리케이션에서는 일반적으로 제어권이 개발자에게 있다.

```java
public class Example {
    public static void main(String[] args) {
        System.out.println("Hello IoC!");
    }
}
```

위의 코드처럼 개발자가 작성한 코드를 순차적으로 실행하는 것이 일반적인 제어 흐름이다.

### **Java 웹 애플리케이션에서의 제어권**

웹 애플리케이션에서는 엔트리 포인트에서, 클라이언트의 요청이 들어올 때마다 서블릿 컨테이너 내의 컨테이너 로직이 서블릿을 직접 실행시킨다.

이 경우 서블릿 컨테이너가 서블릿을 제어하기 때문에 애플리케이션의 주도권은 서블릿 컨테이너에 있다.

---

곧바로 이해하기엔 조금 난해한 개념 같다. 추후 학습을 통해 더 많은 예시를 살펴보고 개념을 익히도록 하자.

> **지금의 이해를 바탕으로 추측해보는 IoC의 개념  
> 애플리케이션의 주도권(이런 저런 것들을 처리하는 권한)은 프레임워크에 맡기고 개발자는 핵심 로직에 집중한다.**

---

### **의존성 주입**

객체 지향 프로그래밍에서의 의존성은 대부분 객체 간의 의존성을 의미한다.

**예를 들어, A라는 클래스가 B 클래스의 메서드를 호출하면, A클래스가 B클래스에 의존한다고 표현한다.**

그렇다면 의존성을 '주입'한다는 것은 어떤 의미일까?

```java
public class CafeClient {
    public static void main(String[] args) {
        MenuService menuService = new MenuServiceStub();
        MenuController controller = new MenuController(menuService);
    }
}
```

4번째 line을 보면, 3번째 line에서 생성한 menuService라는 객체가 controller라는 객체의 생성자로 전달된다. 이처럼 생성자를 통해 어떤 클래스의 객체를 전달 받는 것을 **'의존성 주입'**이라고 한다.

의존성 주입을 사용할 때, 항상 염두에 두어야 하는 부분이 있다.

> **클래스 내부에서 외부 클래스의 객체를 생성하기 위해 new 키워드를 쓸지 말지 여부를 결정  
> (Reflection이라는 기법을 사용함으로써, new키워드를 사용하지 않고도 Runtime에서 객체를 동적으로 생성할 수 있는 방법도 존재한다.)**

애플리케이션 코드 내부에서 직접적으로 new 키워드를 사용할 경우 객체지향 설계의 관점에서 중요한 문제가 발생할 수 있다.

> **A클래스가 B클래스의 객체를 전달받음으로써 의존성 주입을 구현할 때 new 키워드를 직접적으로 사용한 상황에서, B클래스가 아닌 C클래스를 의존해야 한다면?**

코드가 짧고 수정해야 할 부분이 적다면 직접적으로 수정하는 것이 그렇게까지 어렵진 않겠지만, 규모가 큰 경우라면 상당히 곤역을 치를 것이다. 이처럼 new 키워드를 사용하여 의존 객체를 생성할 때, 클래스 간에 강하게 결합(Tight Coupling)되어있다고 표현한다.

위의 예시를 보면 기본적으로 객체간의 결합은 느슨한 것이 좋아보인다. 

### **느슨한 의존성 주입**

느슨한 의존성 주입은 인터페이스를 통해 구현할 수 있다. A클래스가 B클래스를 직접적으로 의존하는 것이 아니라 인터페이스를 의존하도록 하고, 해당 인터페이스의 구현체를 B클래스로 지정하는 것이다. 아래의 예시를 보면 이해가 더 쉬울 것 같다.

```java
public class CafeClient {
    public static void main(String[] args) {
        MenuService menuService = new MenuServiceStub();
        MenuController controller = new MenuController(menuService);
    }
}
```

```java
public class MenuController {
    private MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }
    public List<Menu> getMenus(){
        return menuService.getMenuList();
    }
}
```

```java
public interface MenuService {
    List<Menu> getMenuList();
}
```

```java
public class MenuServiceStub implements MenuService {
    @Override
    public List<Menu> getMenuList() {
        return List.of(
                new Menu(1, "아메리카노", 2500),
                new Menu(2, "카라멜 마끼아또", 4500),
                new Menu(3, "바닐라 라떼", 4500)
        );
    }
}
```

MenuController는 생성자로 MenuService 인터페이스를 주입받는다. 그 뒤 MenuServiceStub 클래스에서 해당 인터페이스를 구현함으로써 MenuServiceStub 클래스를 MenuController 클래스에 주입하고 있다.

> **느슨한 결합 - 하나의 완충 장치를 넣음으로써, 직접적인 결합을 피하고 코드의 유연성을 높인다.**

하지만 아직도 코드에는 new 키워드가 있다. 이건 어떻게 처리할까?

> **"처리는 Spring이 대신 해준다"**

## **IoC/DI 핵심 요약**

-   애플리케이션 흐름의 주도권이 사용자가 아니라 Framework나 서블릿 컨테이너 등 외부에 있는 것을 IoC라고 한다.
-   DI는 IoC 개념을 구체화 시킨 것으로, 객체 간의 관계를 느슨하게 해준다.
-   클래스 내부에서 new를 사용해 참조할 클래스의 객체를 직접 생성하지 않고, 생성자 등을 통해 외부에서 다른 클래스의 객체를 전달 받고 있다면 의존성 주입이 이루어지고 있는 것이다.
-   new키워드를 사용하여 객체를 생성할 때, '클래스들이 강하게 결합되어있다' 고 표현한다.
-   어떤 클래스가 인터페이스 같이 일반화 된 구성 요소에 의존하고 있을 경우, '클래스들이 느슨하게 결합되어있다' 고 표현한다.
-   객체간의 느슨한 결합은 요구 사항의 변경에 유연하게 대처할 수 있도록 해준다.
-   의존성 주입은 클래스간의 강한 결합을 느슨한 결합으로 만들어준다.
-   Spring에서는 애플리케이션 코드에서 이루어지는 의존성 주입을 Spring이 대신 해준다.(꿀)

## **AOP(Aspect Oriented Programming)**

관점 지향 프로그래밍, 혹은 관심 지향 프로그래밍 정도로 해석할 수 있다. 애플리케이션 개발 시 각각의 세부적인 부분에 관계없이 전반에 걸쳐 공통적으로 사용되는 기능들이 있기 마련인데, 이러한 공통 기능에 대한 사항이 AOP가 다룰 대상이다.

### **공통 관심 사항과 핵심 관심 사항**

단도직입적으로, 핵심 관심 사항은 흔히들 말하는 비즈니스 로직, 즉 애플리케이션의 주 목적을 달성하기 위한 핵심 로직에 대한 관심사를 일컫는다.

AOP가 다루게 될 공통 관심 사항은 부가적 관심 사항이라고 표현하기도 하는데, 일반적인 공통 관심 사항은 아래와 같다.

-   로그를 남기는 기능
-   보안에 관련된 기능
-   트랜잭션과 관련된 기능

해당 기능들은 특정 기능에만 적용되는 것이 아니라, 애플리케이션 전반에 걸쳐 사용될 것이다. 하지만 이 기능들을 각각의 세부적인 코드들에 모두 집어넣는다면, 코드가 지저분할 뿐 아니라 기능을 수정하거나 유지, 보수 시 굉장히 귀찮아질 것이다. 결론적으로 '객체 지향 설계 원칙에 맞지 않은 코드'가 되어버린다는 것이다.

그렇다면 핵심 로직에서 공통 기능을 분리하는 AOP를 통해 아래와 같은 장점을 가질 수 있을 것이다.

-   코드의 간결성 유지, 코드의 재사용 가능
-   위의 항목으로 인한 유지 및 보수의 수월함
-   객체 지향 설계 원칙에 맞는 코드 구현 가능

## **AOP 핵심 요약**

-   AOP의 Aspect는 애플리케이션의 공통 관심사를 의미한다.
-   애플리케이션의 공통 관심사란, 비즈니스 로직을 제외한 앱 전반에 걸쳐서 사용되는 공통 기능들을 의미한다.
-   공통 기능에는 로깅, 보안, 트랜잭션, 모니터링, 트레이싱 등이 있다.
-   AOP를 통해 코드의 간결성, 코드의 재사용, 객체 지향 설계 원칙에 맞는 코드 구현등의 이득을 얻을 수 있다.

## **PSA(Portable Service Abstraction)**

PSA는 '일관된 서비스 추상화'로 해석할 수 있다. 추상화는 Java 학습 중 상속, 캡슐화, 다형성과 함께 학습했다. 그럼 일관된 서비스 추상화가 무엇을 의미하는지만 알면 될 것이다.

```java
// DbClient.java
public class DbClient {
    public static void main(String[] args) {
        // Spring DI로 대체 가능
        JdbcConnector connector = new MariaDBJdbcConnector(); // (1)

        // Spring DI로 대체 가능
        DataProcessor processor = new DataProcessor(connector); // (2)
        processor.insert();
    }
}

// DataProcessor.java
public class DataProcessor {
    private Connection connection;

    public DataProcessor(JdbcConnector connector) {
        this.connection = connector.getConnection();
    }

    public void insert() {
        // 실제로는 connection 객체를 이용해서 데이터를 insert 할 수 있다.
        System.out.println("inserted data");
    }
}

// JdbcConnector.java
public interface JdbcConnector {
    Connection getConnection();
}

// MariaDBJdbcConnector.java
public class MariaDBJdbcConnector implements JdbcConnector {
    @Override
    public Connection getConnection() {
        return null;
    }
}

// OracleJdbcConnector.java
public class OracleJdbcConnector implements JdbcConnector {
    @Override
    public Connection getConnection() {
        return null;
    }
}
```

위 코드에서는 MariaDBJdbcConnector 구현체의 객체를 생성해 JdbcConnector 인터페이스 타입의 참조변수에 할당하고있다. 그 후 실제로 데이터를 데이터베이스에 저장하는 기능을 하는 DataProcessor 클래스의 생성자로 JdbcConnector 객체를 전달하여 의존성을 주입하고 있다.

만약, MariaDB가 아니라 Oracle을 사용하고 싶다면, (1)에서 JdbcConnector 타입의 참조변수 connector에 OracleJdbcConnector 의 객체를 생성하여 할당하면 될 것이다.

이 코드에서 DbClient는 실제로 데이터를 처리하는 주체가 MariaDB인지, Oracle인지에 관계없이 JdbcConnector만 가져오고있다. 이렇게 추상화된 상위 클래스를 일관되게 바라보며 하위 클래스의 기능을 사용하는 것이 "일관된 서비스 추상화(PSA)"의 기본 개념이다.

## **PSA 핵심 요약**

-   java의 객체지향 개념에서 학습했듯이, 추상화는 어떤 클래스의 본질적인 특성만을 추출하여 일반화하는 것을 의미한다.
-   클라이언트가 추상화된 상위 클래스를 일관되게 바라보며 하위 클래스의 기능을 사용하는 것이 PSA이다.
-   애플리케이션에서 특정 서비스를 이용할 때, 서비스의 기능에 접근하는 방식을 일관되게 유지함으로써 기술은 유연하게 사용할 수 있도록 하는 것이 PSA의 지향점이다.
-   PSA가 필요한 주된 이유는 결국 유지보수를 위함이다. 사용되는 기술이 변경되더라도 코드는 최소한의 변경만을 통해 요구 사항을 달성할 수 있다.
