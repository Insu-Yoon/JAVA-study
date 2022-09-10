# 추상화(Abstraction)
### 추상화 : 기존 클래스들의 공통적인 요소들을 골라서 상위 클래스를 만들어 내는 것
java에서는 추상 클래스(abstract class)와 인터페이스(interface)를 통해 추상화를 구현한다
***
### 추상 클래스(Abstract Class)
추상 클래스는 추상 메서드를 하나 이상 갖는 메서드를 지칭한다. 일반 클래스와 비교시 추상 클래스의 특징은 다음과 같다.
* 추상 메서드를 하나이상 가진다
  * 추상 메서드는 body를 갖지 못한다.(body부분은 하위클래스에서 강제로 오버라이딩하여 완성한다)
* 생성자는 가지지만, 인스턴스 생성은 불가능하다
```java
public abstract class Example{
    String name;
    int age;
    abstract void doWork();
}
class Sub extends Example{
    @Override
    void doWork(){
      //overriding
    }
}
```
하위클래스 Sub에서는 반드시 doWork()를 오버라이딩하여 완성시켜야 한다.

### 인터페이스(Interface)
추상클래스보다도 추상성이 높은 형식. 인터페이스는 멤버로 상수 추상메서드만을 가진다. (java 8 이후로는 default/static 메서드도 멤버로 가질 수 있게 되었다)   
인터페이스는 implements 키워드를 통해 클래스에 적용(구현)가능하며, **상속과 다르게 다중 구현이 가능하다.**
```java
interface inter_face{//인터페이스
    abstract void ab_method2();//추상메서드
}
class B implements inter_face{//인터페이스를 구현한 클래스
    @Override
    public void ab_method2() {
        System.out.println("오버라이딩이 강제됨");
    }
}
```
아래 이미지는 내가 이해한 상속, 추상클래스, 인터페이스를 바탕으로하여, 계층 구조를 구현화하는 모습이다.   
   
![img](https://blog.kakaocdn.net/dn/kKgSZ/btrLLWDPit2/uGOd63S8DWxqMoxr4DXXU1/img.png)   
   
* 공통적인 부분은 필드와 메서드를 통해 표현하고, 상속으로 넘겨주면 된다.   
* 비슷한 기능이지만 하위클래스마다 차이점을 두고싶거나 해당 메서드를 반드시 재정의하게 만들고싶다면 abstract 메서드를 만들어서 관리하면 된다.   
* 기능을 추가하고 싶은데 해당 기능이 특정 클래스에만 한정적으로 적용되는게 아니라 여러 클래스가 공통적으로 가져야 할 기능이라면 인터페이스를 통해 추상 메서드를 만들고 각 클래스에 인터페이스를 implement해 오버라이딩하도록 만들면 된다.
