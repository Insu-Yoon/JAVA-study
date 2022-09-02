# Class
클래스는 객체를 정의한 틀이다. 객체(더 정확히 말하면 인스턴스)는 클래스가 정의한대로 생성된다.   
```java
public class FormOfClass {
	int i = 10; // >> 필드(변수)
void printfield() {
    동작내용 // >> 메서드
    }
ExampleClass {...} // >> 생성자
class InnerClass {...} // >>내부 클래스
} 
```
클래스는 위와 같이 구성된다.   
## 객체의 생성
```java
클래스명 참조변수명; //인스턴스를 참조하기 위한 참조변수를 선언
참조변수명 = new 생성자(); //인스턴스를 생성하고 객체의 주소를 참조변수에 저장
//example
Car my_car;
my_car = new Car();
```
## 객체의 활용
```java
참조변수명.필드명; //필드(변수)의 값을 가져옴
참조변수명.메서드명; // 메서드를 호출함
```
## 인스턴스 변수와 스태틱 변수
```java
class TypeOfVariable {
	int instance_variable; // 인스턴스 변수
	static int static_variable; // 스태틱 변수

	void method() {
		int local_variable = 0; // 지역 변수
	}
}
```
* 인스턴스 변수는 일반적인 변수와 같다. 각 인스턴스의 고유한 값을 저장하기위해 사용한다.   
인스턴스 변수는 new 생성자(); 를 통해 인스턴스가 생성될 때 만들어진다.
* 스태틱 변수는 TypeOfVariable 클래스로 생성된 모든 인스턴스에 있어 값을 공유한다.          
스태틱 변수는 인스턴스를 생성하지 않고도 "클래스명.변수명" 을 통해 사용가능하다.
* 지역 변수는 메서드 내부에서만 사용가능하다.
## Method Overloading
```java
class Shape {
    public void area(int r) {
        System.out.println("원의 넓이 = " + (3.14 * r * r));
    }
    public void area(int a, int b) {
        System.out.println("직사각형의 넓이 = " + (a * b));
    }
    public void area(double a, double b) {
        System.out.println("삼각형의 넓이 = " + (a* b /2));
    }
}
```
위와 같이 클래스 내에서 이름은 같고 매개변수의 갯수나 type이 다른 여러개의 메서드를 정의하는 것을 Method Overloading이라고 한다.   
같은 이름의 메서드로 여러가지 타입의 매개변수에 대응할 수 있으며 결정적으로 메서드의 이름을 고민할 수고가 줄어든다(...)
