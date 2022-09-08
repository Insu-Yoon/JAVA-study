# 다형성(Polymorphism)
### 다형성이란 한 타입의 참조 변수로 여러 타입의 객체를 참조할 수 있는 특성을 말한다.
이러한 특성은 기본적으로 두 개의 클래스가 상속관계에 있을 때 성립한다.   
```java
public class Example {
    public static void main(String[] args) {
        Vehicle car = new Car();
        Vehicle bike = new Bike();
        car.go();
        bike.go();
        car.goBack(); // Vehicle형 참조변수이므로 error 
    }
}
class Vehicle{
    void go(){
        System.out.println("간다");
    }
}
class Car extends Vehicle{
    void go(){
        System.out.println("자동차가 간다");
    }
    void goBack(){
        System.out.println("뒤로갑니다.");
    }
}
class Bike extends Vehicle{
    void go(){
        System.out.println("바이크가 간다");
    }
}
```
위 코드에서 Vehicle은 상위클래스, Car와 Bike는 하위클래스다. 코드의 3,4번째 줄을 보자
```java
Vehicle car = new Car();
Vehicle bike = new Bike();
```
참조변수의 type과 객체의 type이 다른 것을 확인할 수 있다.   
이것이 java에서 다형성이 어떻게 구현되는 가를 보여준다.   
Car 타입의 객체는 기본적으로 Car 타입의 참조변수를 갖지만, 상위 클래스 타입인 Vehicle 타입을 참조변수로 가질 수 있다.   
이렇게 참조 변수의 타입을 바꾸는 것을 캐스팅이라고 표현하며, 하위클래스 타입의 참조변수를 상위클래스 타입의 참조변수로 바꾸는 것을 Up-casting이라고 표현한다.   
반대로 상위클래스 타입의 참조변수를 하위클래스 타입의 참조변수로 바꾸는 것이 Down-casting인 것은 쉽게 예측 가능하다.   
   
**반드시 기억해야 할 점은, 다운 캐스팅을 하기 위해서는 해당 객체에 업 캐스팅이 전제된다는 것이다.**    
```java
Vehicle car = new Car(); // 업캐스팅된 객체 Car();
Car car2 = (Car)car; // 다시 다운캐스팅(결국 car든, car2든 가르키는 객체는 Car()로 똑같다)
```
업캐스팅에는 타입을 명시하지 않아도 되지만, 다운캐스팅시에는 반드시 변환할 참조변수의 타입을 명시해야한다.   
***
### 다형성이 갖는 장점
다형성을 적용하지 않은 기본 코드를 먼저 보자
```java
public class Example {
    public static void main(String[] args) {
        Vehicle car = new Car();
        Vehicle bike = new Bike();
        car.go();
        bike.go();
        (Car)car.goBack(); // Vehicle형 참조변수이므로 error
        ((Car) car).goBack();
    }
}
class Vehicle{
    void go(){
        System.out.println("간다");
    }
}
class Car extends Vehicle{
    void go(){
        System.out.println("자동차가 간다");
    }
    void goBack(){
        System.out.println("뒤로갑니다.");
    }
}
class Bike extends Vehicle{
    void go(){
        System.out.println("바이크가 간다");
    }
}
class Test{
    public static void test(Car car){
        car.go();
    }
    public static void test(Bike bike){
        bike.go();
    }
}
```
main에는 다형성이 적용되어 있지만 무시하도록 하자   
맨 아래의 Test 클래스를 보면 Car 타입, Bike 타입의 매개변수를 받아 메서드를 실행하는 것을 확인할 수 있다.   
지금은 두개뿐이지만 Bus, Train, Subway, Airplane 등 수많은 클래스들이 생긴다면 저기에 일일히 각 케이스를 다 입력해야 할 것이다.   
하지만 다형성을 활용하면 다음과 같이 간단하게 줄일 수 있다.
```java
class Test {
    static void test(Vehicle vehicle) {
        vehicle.go();
    }
}
```
이것이 다형성의 장점을 보여주는 예시이며, 다형성이 OOP를 형성하는 기둥 중 하나인 이유일 것이다.
