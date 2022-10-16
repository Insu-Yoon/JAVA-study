> **DI(Dependency Injection) : 의존성 주입. 대표적인 형태는 생성자에 외부 객체를 주입하는 방법**

이전 글에서 Spring의 POJO에 대해 학습하며 DI가 무엇인지, 어떤 방법으로 구현되는지 다뤘다.

이번 글에서는 실제로 코드를 작성하며 의존성 주입을 연습하고, 관련된 Spring의 내용에 대해서도 정리해보자.

# **직접 설정 파일 작성(java)**

```java
//***코드 1***
public class MemberService {
    private final MemberRepository memberRepository = new MemberRepository();

    public void createMember(Member member) {
        memberRepository.postMember(member);
    }

    public Member getMember(Long memberId) {
        return memberRepository.getMember(memberId);
    }

    public void deleteMember(Long memberId) {
        memberRepository.deleteMember(memberId);
    }
}
```

 '코드 1' 에서 MemberService 클래스는 MemberRepository의 객체를 직접적으로 의존하고 있으며, 아래의 세 가지 메서드는 모두 memberRepository 객체에서 구현한 메서드를 사용한다.

만약 이러한 상황에서 MemberRepository 라는 객체를 MockRepository라는 객체로 교체해야 한다면 어떻게 될까?

> 지금처럼 해당 객체에 의존하는 객체가 적어, 코드 몇 줄을 수정하는 정도에서 그친다면  
> new MemberRepository()를 new MockRepository()로 직접 수정함으로써 교체가 가능하다.  
> 물론 교체한 객체에 위에서 사용한 메서드들이 정의되어 있어야 할 것이다.

교체한 객체를 정의한 클래스에 MemberService가 필요로 하는 메서드가 정의되어있지 않거나, 교체되어야 할 객체가 여기저기서 의존되고 있다면 수정에 많은 자원이 필요해진다.

이러한 문제를 의존성 주입을 통해 해결할 수 있다.

```java
//***코드 2***
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void createMember(Member member) {
        memberRepository.postMember(member);
    }

    public Member getMember(Long memberId) {
        return memberRepository.getMember(memberId);
    }

    public void deleteMember(Long memberId) {
        memberRepository.deleteMember(memberId);
    }
}
```

'코드 2' 에서는 MemberReppository 객체를 생성자로 받아온다. 외부로부터 의존성이 주입된 것이다.

의존성 주입을 통해 객체간의 결합도(의존성)가 낮아지므로, 객체를 교체함으로써 발생하는 문제를 해결할 수 있다.

위의 코드만으로는 '그냥 같은 클래스를 new로 생성하느냐, 외부에서 생성자로 받아오느냐의 차이일 뿐인데 결합도가 어떻게 낮아질까? 그리고 같은 클래스를 내부에서 new 키워드로 생성하느냐, 생성자로 받아오느냐에 따라 정의된 메서드의 차이가 있을리가 없는데 해당 문제는 어떻게 해결된다는 것일까?' 라는 의문이 생길 수 있다.

일반적으로 생성자를 통해 전달되는 객체의 타입은 인터페이스로 추상화가 되어있다. 따라서 전달되는 객체들은 모두 해당 인터페이스의 구현체이므로, 구현된 기능이 동일하여 메서드와 관련된 부분도 걱정할 필요가 없으며 결정적으로 애플리케이션의 제어권을 외부에서 가져가기 때문에 제어 역전이 달성된다. 따라서 결합도 또한 자연스레 낮아지는 것이다.

조금 더 와닿게 설명하기는 아직 어렵지만, 우선 'IoC를 만족시켰으니, 결합도가 낮아졌다. 이는 POJO 프로그래밍의 관점에서 올바른 방향이다.' 라는 느낌으로 이해해두자.

만약 이미 MemberService의 객체를 다른 클래스에서 의존하고 있는 경우(MemberService의 객체를 활용하거나, 메서드를 사용하거나)였다면 해당 클래스엔 아무런 영향이 없을까?

>  **코드 1에서 코드 2로 변경하면서, 다른 클래스에도 영향이 있을 것이다.**

아래 코드를 보자.

```java
//***코드 3***
public class MemberTest {
  public static void main(String[] args) {
    MemberService memberService = new MemberService();

    Member member = new Member(0L, "lucky@codestates.com", "KimLucky", "010-1234-5678");
    memberService.createMember(member);

    Member currentMember = memberService.getMember(0L);

    System.out.println("회원 가입한 유저 : " + member.getName());
    System.out.println("현재 첫번째 유저 : " + currentMember.getName());

    if(member.getName().equals(currentMember.getName())) {
      System.out.println("새롭게 가입한 사용자와 현재 사용자가 같습니다.");
    }

    memberService.deleteMember(0L);

    if(memberService.getMember(0L) == null) {
      System.out.println("회원 삭제가 정상적으로 완료되었습니다.");
    }
  }
}
```

코드 3에서는 MemberTest 클래스 내에서 MemberService의 객체를 생성하고, 해당 객체의 메서드를 사용하고 있다. 코드 변경 전에는 MemberService 클래스 내에서 필요한 객체를 생성하여 사용하였기 때문에 MemberService의 객체를 생성할 때 위의 코드처럼 생성할 수 있지만, 코드 1을 코드 2처럼 변경한 뒤라면 생성자에 MemberRepository를 전달해야 한다.

물론 수동으로 MemberRepository 객체를 생성하고 생성자에 넣어주면 해결이 가능하지만, 그러면 객체를 생성할 때 마다 주입할 객체를 직접 작성해야한다. 의존성 주입을 사용하는 의미가 사라지는 것이다.

> **그럼 이 문제는 어떻게 해결할까?**
> **일차적으로, 의존성 주입을 관리하는 설정 파일을 만들어, 관심사 분리를 통해 해결할 수 있다.**

AOP의 개념을 적용한다고 생각할수도 있어보인다. 따로 클래스를 만들어, 의존성과 관련된 부분들을 처리하고 해당 클래스를 가져다가 쓰는 것이다.

```java
//***코드 4***
import di.exercise.member.MemberRepository;
import di.exercise.member.MemberService;

public class DependencyConfig {

    public MemberRepository memberRepository() {
        return new MemberRepository();
    }

    public MemberService memberService() {
        return new MemberService(memberRepository());
    }
}
```

코드 4에서는 DependancyConfig 클래스를 정의하고, 해당 클래스 내에서 MemberRepository를 생성하여 MemberService의 생성자에 주입한다. 이제 MemberTest클래스에서 Dependency 객체를 생성하고, 해당 객체의 메서드를 사용해 MemberService 객체를 만들어 사용할 수 있게 되었다.

또한 MemberService 입장에서는 생성자를 통해 어떤 구현 객체가 주입될 지 알 수 없으며, 알 필요도 없다. 어떤 객체가 주입될지는 외부(지금은 DependencyConfig)에서 결정하게 된다.

이제 MemberService 클래스는 실행에만 집중할 수 있게 된 것이다.

```java
public class MemberTest {
	public static void main(String[] args) {
		DependencyConfig dependencyConfig = new DependencyConfig();
		MemberService memberService = dependencyConfig.memberService();
    	...
	}
}
```

지금까지의 과정을 돌아보며 개념을 다시 익혀보자

1.  기존 코드에서는 객체간 결합도가 높았다. 이는 객체 지향 설계 원칙에 맞지 않다.
2.  기존의 의존 방식을 버리고, DI를 통해 IoC를 달성한다. (생성자로 외부 객체를 주입하며, 무엇이 주입되는지는 외부에서 결정)
3.  기존에 작성된 다른 클래스에서 문제 발생 - AOP 개념 적용, 의존성을 따로 관리하는 클래스를 만든다.
4.  객체간의 의존이 아닌, 각각의 객체가 의존성 관리 클래스에 의존하도록 수정(일종의 PSA 개념?)

프레임워크의 도움 없이 직접 설정 파일을 통해 의존성 주입을 할 수 있는 방식을 사용해보았다.

이제 스프링에서 지원하는 DI를 위한 내용들을 정리하고, 코드로 작성해보자.

# **Spring과 DI**

먼저 다음의 개념들을 학습하고 넘어가자.

-   스프링 컨테이너(Spring Container)
-   빈(Bean)

> **아래의 Spring Container 내용에서 빈(Bean)에 대한 내용도 나온다.**  
> **우선은 '객체'라는 개념으로 생각하고 읽으면 되겠다.**

## **Spring Container**

-   스프링 프레임워크의 핵심 컴포넌트이다.
-   내부에 존재하는 애플리케이션 빈의 생명주기를 관리한다. (Bean 생성, 관리, 제거 등의 역할을 담당)
-   ApplicationContext 를 스프링 컨테이너라고 하며, 이는 인터페이스로 구현되어 있다.
-   스프링 컨테이너는 XML, 애너테이션 기반의 자바 설정 클래스로 만들 수 있다.
-   스프링 컨테이너를 통해 원하는 만큼 많은 객체를 가질 수 있다.
-   의존성 주입을 통해 애플리케이션의 컴포넌트를 관리한다.  
    -   스프링 컨테이너는 서로 다른 빈을 연결해, 애플리케이션의 빈을 연결하는 역할을 한다.
    -   개발자는 모듈 간의 의존 및 결합으로 인해 발생하는 문제로부터 자유로울 수 있다.
    -   메서드가 언제, 어디서 호출되어야 하는지, 메서드를 호출하기 위해 필요한 매개변수를 준비해서 전달하지 않는다.(?)
-   스프링 컨테이너를 통해 객체 간의 결합도(의존성)을 낮출 수 있다.  
    → 스프링 컨테이너가 객체들을 관리하는 인터페이스이며, 의존성 또한 스프링 컨테이너에서 관리하기 때문

## **Spring Container의 종류**

-   BeanFactory
    -   스프링 컨테이너의 최상위 인터페이스
    -   빈을 등록, 생성, 조회, 돌려주는 등 빈을 관리하는 역할
    -   getBean() 메서드를 통해 빈을 인스턴스화 할 수 있다.
    -   @Bean이 붙은 메서드의 이름을 스프링 빈의 이름으로 사용해 Bean 등록을 한다.
-   ApplicationContext
    -   BeanFactory의 기능을 상속받아 제공한다.
    -   BeanFactory 기능 외의 부가기능을 제공
        -   MegssageSource : 메세지 다국화를 위한 인터페이스
        -   EnvironmentCapable : 개발, 운영 등을 환경변수 등으로 나눠 처리하고 애플리케이션 구동 시 필요한 정보들을 관리하기 위한 인터페이스
        -   ApplicationEventPublisher : 이벤트 관련 기능을 제공
        -   ResourceLoader : 파일, 클래스 경로, 외부 등 리소스를 편하게 조회

## **컨테이너의 인스턴스화**

애너테이션 기반의 컨테이너는 다음과 같이 인스턴스화 할 수 있다.

```java
ApplicationContext context = new AnnotationConfigApplicationContext(DependencyConfig.class);
```

이 때, 생성자에 들어가는 파라미터는, 컨테이너가 로컬 파일 시스템이나 자바 클래스 경로 등과 같은 다양한 외부 리소스로부터 구성 메타데이터를 로드할 수 있도록 하는 '리소스 문자열' 이다.

## **빈(Bean)**

-   스프링 컨테이너에 의해 관리되는 재사용 소프트웨어 컴포넌트
-   인스턴스화된 객체를 의미한다.
-   @Bean이 적힌 메서드를 모두 호출한 뒤, 반환된 객체를 스프링 컨테이너에 등록한다.
-   빈은 클래스의 등록정보, getter/setter 메서드를 포함한다.
-   빈은 컨테이너에 사용되는 설정 메타데이터로 생성된다.
    -   설정 메타데이터 : XML 또는 자바 애너테이션, 자바 코드로 표현되며 컨테이너의 명령과 인스턴스화, 설정, 조립할 객체를 정의한다.

## **bean에 접근하는 방법**

ApplicationContext를 사용하여 bean 정의를 읽고 액세스할 수 있다.

```java
// create and configure beans - 컨테이너 인스턴스화, 구성 메타데이터 로드
ApplicationContext context = new ClassPathXmlApplicationContext("services.xml", "daos.xml");

// retrieve configured instance - 구성된 인스턴스 가져오기
PetStoreService service = context.getBean("memberRepository", memberRepository.class);

// use configured instance - 구성된 인스턴스 사용
List<String> userList = service.getUsernameList();
```

getBean()을 사용하여 bean의 인스턴스를 가져올 수 있으며, 이외에도 ApplicationContext 인터페이스에는 bean을 가져오는 몇 가지 방법들이 더 존재한다.

실제적으로 응용 프로그램 코드에서는 getBean() 메서드로 호출하여 사용하면 안된다

-   왜일까? - 파라미터로 입력한 타입을 찾지 못하면 에러가 발생해서? 

## **BeanDefinition**

스프링은 BeanDefinition이라는 추상화 덕분에, 다양한 설정 형식을 지원할 수 있다.

-   빈은 BeanDefinition(빈 설정 메타정보)으로 정의되고, BeanDefinition에 따라 활용하는 방법이 달라진다.
-   BeanDefinition  
    -   속성에 따라 컨테이너가 Bean을 어떻게 생성하고 관리할 지 결정한다.
    -   @Bean 하나당 1개씩 메타 정보가 생성된다.
    -   Spring이 설정 메타정보를 BeanDefinition 인터페이스를 통해 관리하므로, 컨테이너 설정을 XML 또는 Java로 할 수 있다. - 스프링 컨테이너는 설정 형식이 XML인지 Java인지 몰라도, BeanDefinition만 알면 된다.

## **Container, Bean 종합 정리**

-   컨테이너란 내부에 또 다른 컴포넌트를 지닌 컴포넌트를 의미한다.
-   컨테이너는 먼저 객체를 생성하고 객체를 서로 연결한다.
-   컨테이너는 객체를 설정하는 단계를 지나, 생명주기 전반을 관리한다.
-   컨테이너는 객체의 의존성을 확인해 생성한 뒤, 적절한 객체에 의존성을 주입한다.
-   스프링은 스프링 컨테이너를 통해 객체를 관리한다.
-   스프링 컨테이너에서 관리되는 객체를 빈이라고 한다.
-   빈은 클래스의 등록 정보, getter/setter 메서드를 포함한다.
-   빈은 컨테이너에 사용되는 설정 메타데이터를 통해 생성된다.

> **지금까지의 이해내용** 
>   
> **DI를 얘기하다말고 왜 Spring Container와 Bean에 대한 내용이 나왔을까? 라는 의문에 대해 이제 어느정도 감이 잡히는 듯 하다.**  
>   
> **Spring에서는 스프링 컨테이너와 스프링 빈을 통해 객체간의 의존성을 관리한다. (물론 그 외에도 많은 것을 관리하겠지만.)** 
> **이 때, 각 객체가 메타데이터에 따라 인스턴스화 되어 Bean이 된다. 이 Bean은 Container의 관리하에 놓이게 되며, Container는 입력된 메타데이터에 따라 Bean을 관리한다. --> Bean 생성시에도 Container가 관여한다?**

## 빈 스코프(Bean Scope)
Bean definition -> 하나의 레시피
-  특정 bean definition에서 생성된 개체에 연결할 다양한 의존성 및 구성 값 뿐아니라, 특정 bean definition에서 생성된 개체의 범위도 제어할 수 있다.
-  Spring Framework는 6개의 범위를 지원하며, 그중 4개는 ApplicationContext를 사용하는 경우에만 사용할 수 있다.
-  bean은 여러 범위 중 하나에 배치되도록 정의할 수 있다.
-  구성을 통해 생성하는 개체의 범위를 선택하므로, 강력하고 유연하다.
-  사용자 정의 범위를 설정할 수도 있다.

### 싱글톤 스코프(Singleton Scope)
> 클래스의 인스턴스가 단 하나만 생성되는 것을 보장하는 디자인 패턴

-  싱글톤 스코프는 Bean Scope 중 Default 스코프이다.
-  스프링 컨테이너의 시작과 함께 생성되며프링 컨테이너가 종료될 때 까지 유지된다.
-  싱글톤 빈의 경우 하나의 공유 인스턴스만 관리하게 된다.
	- private 생성자를 사용하여, 외부에서 new를 통해 객체를 생성할 수 없도록 막아야한다.
-  해당 bean definition과 일치하는 ID 또는 ID를 가진 빈에 대한 모든 요청은 스프링 컨테이너에서 해당 특정 빈 인스턴스를 반환한다.

```
싱글톤 빈이 아닌 경우, 호출될 때 마다 새로운 객체 생성 -> 자원 낭비
사실 하드웨어의 발전 덕에 어지간한 경우가 아니면 문제가 발생하지 않는다(로직상에 문제가 생기는 경우 제외)
하지만 효율적이면 좋잖아..? -> 싱글톤 굿
```

# Java기반 컨테이너 설정
-  @Configuration - 해당 클래스가 설정 정보를 담은 클래스임을 표시
-  @Bean - 해당 애너테이션이 붙은 메서드를 Bean으로 등록해라! 라고 전달

# Component Scan (Spring 기능)
설정 정보 없이도 자동으로 스프링 빈을 등록할 수 있는 기능 - 스프링이 제공한다.
-  @ComponentScan - @Component 애너테이션이 붙은 컴포넌트를 가져온다.
-  @Component - 스프링 빈으로 등록할 클래스에 붙인다.
	-  @Controller, @Service, @Repository, @Configuration 등 해당 컴포넌트의 용도에 따라 구체적으로 애너테이션 작성
-  @Autowired - 생성자 의존성 주입에 필요한 설정 정보 대신, 의존관계 자동 주입을 해준다.

> @Component vs. @Bean
> @Component는 클래스에 적용 가능, @Bean은 메서드에 적용가능 - 적용하는 레벨이 다르다.
> 외부 라이브러리 등 클래스에 @Component를 붙일 수 없다? -> 반환되는 객체를 @Bean으로 처리


# 다양한 의존 관계 주입 방법
> 4가지의 의존관계 주입 방법이 존재한다.
> -   생성자 주입
> -   수정자 주입(setter 주입)
> -   필드 주입
> -   일반 메서드 주입

위의 4가지 방법 중, 생성자 주입이 가장 대표적이고 많이 사용되는 주입 방법이다.   
수정자 주입의 경우 필요에 따라 사용될 수 있으나, 그 빈도는 비교적 매우 적다고 한다.   
나머지인 필드 주입, 일반 메서드 주입의 경우 사실상 사용되지 않는 방법이다.

-  생성자 주입 
	-  불변, 필수 의존 관계에서 사용
	-  생성자 호출 시점에 단 한번만 호출된다.
	-  생성자가 하나 밖에 없으면, @Autowired 애너테이션을 생략해도 된다.
	-  NPE를 방지할 수 있다.
	-  주입받을 필드를 final로 선언한다.
   
      
-  수정자 주입
	-  변경, 선택 의존 관계에서 사용
	-  자바빈 프로퍼티 규약의 수정자 메서드 방식을 사용한다.
	-  생성자 대신, set'필드명'() 메서드를 사용하여 의존 관계를 주입한다.
	-  @Autowired 애너테이션을 반드시 달아야 한다.
