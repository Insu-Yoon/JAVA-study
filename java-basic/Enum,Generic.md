# Enum, Generic
### Enum : 여러 상수를 편리하게 선언하기 위해 만들어진 자바의 문법요소

```java
public static final int SPRING = 1;
public static final int SUMMER = 2;
public static final int AUTUMN = 3;
public static final int WINTER = 4;
```
상수를 위와 같이 선언할 경우, 다른 곳에서 사용하는 상수와 리터럴이 중복될 수 있다.   
class를 만들어 이를 방지할 수 있지만, 다음과 같은 상황을 생각해볼 수 있다.   
```java
interface Seasons {
	int SPRING = 1, SUMMER = 2, FALL = 3, WINTER = 4;
}

interface Frameworks {
	int DJANGO = 1, SPRING = 2, NEST = 3, EXPRESS = 4;
}

if (Seasons.SPRING==Frameworks.DJANGO){...}
```
위 코드에서 Seasons와 Frameworks는 아무런 상관이 없는 인터페이스임에도 불구하고, 아래쪽의 if문 처럼 전혀 상관없는 상수들을 비교하여도 error가 발생하지 않는다.   
이러한 것을 '타입 안정성이 떨어진다'라고 표현한다.   
이럴 때 사용하기 좋은 것이 enum이다.
```java
enum Months {JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC}
```
위와 같이 상수를 선언할 경우, Months.JAN 과 같이 호출해야 하므로 위의 문제들이 발생하지 않는다.   

enum에 정의된 메서드는 아래와 같다.
| Return type 	|   메소드(매개변수)   	|                                 설명                                 	|
|:-----------:	|:--------------------:	|:--------------------------------------------------------------------:	|
|    String   	|        name()        	| 열거 객체가 가진, 열거 객체를 정의할 때 사용한 상수 이름을 반환한다. 	|
|     int     	|       ordinal()      	|               열거 객체의 순번(0부터 시작)을 반환한다.               	|
|     int     	|   compareTo(비교값)  	|            주어진 비교값과 비교하여 순번 차이를 반환한다.            	|
|  열거 타입  	| valueOf(String name) 	|                 주어진 문자열의 열거 객체를 반환한다.                	|
|  열거 배열  	|       values()       	|                  모든 열거 객체들을 배열로 반환한다.                 	|

***
### Generic : 하나의 클래스로 모든 타입의 데이터를 저장할 수 있는 인스턴스를 만들 수 있도록, 타입을 일반화 해두는 것
```java
class Generic<T> {
    private T item;
    public Generic(T item) {
        this.item = item;
    }
    public T getItem() {
        return item;
    }
    public void setItem(T item) {
        this.item = item;
    }
}
```
위와 같이 선언될 경우 인스턴스를 생성할 때 원하는 변수형을 지정하여 인스턴스를 생성해도 클래스 하나만으로 모든 인스턴스를 생성할 수 있다.   
   
**주의할 점 : static 변수는 generic으로 만들 수 없다(타입을 T로할 수 없다.) 인스턴스에 따라 type이 달라질 수 있는데, 이는 static 변수에 모순되는 상황이다.**   

제너릭 클래스를 인스턴스화 할 때는 다음과 같이 선언할 수 있다.
```java   
Generic<Integer> num = new Generic<>(10);
Generic<String> str = new Generic<>("Good");
Generic<Boolean> tf = new Generic<>(true);
```
primitive변수형을 타입 매개변수로 사용할 경우, 위와 같이 래퍼 클래스(Wrapper class)를 사용하여야 한다.
***
Generic Class를 사용할 때도 다형성을 적용할 수 있다.
```java
class Vehicle { ... }
class Car extends Vehicle { ... }
class Bike { ... }

class Generic<T> {
    private T item;

    public T getItem() {
        return item;
    }
    public void setItem(T item) {
        this.item = item;
    }
}
public static void main(String[] args) {
		Generic<Car> carGeneric = new Generic<>();
		carGeneric.setItem(new Car());    // 다형성적용
//		carGeneric.setItem(new Bike());   // 에러
}
```
**제한된 제네릭 클래스** : <T extends Vehicle> Vehicle의 하위클래스만 타입으로 지정하도록 제한할 수 있다.   
***
### 제네릭 메서드 : 클래스 내부의 특정 메서드만 제네릭으로 선언할 수 있다.
```java
class Basket {
		...
	public <T> void add(T element) {
		...
	}
}
```
**제너릭 클래스, 제너릭 메서드에서 타입 매개변수가 똑같이 선언되었어도, 각각의 타입 매개변수는 별개의 것이다.**
***
### 와일드카드 : 어떠한 타입으로든 대체될 수 있는 타입 파라미터. <?>로 표현되며 일반적으로 extends, super와 조합하여 사용한다.

```java
<? extends T>
<? super T>
```
extends는 **T와 T를 상속받는 하위 클래스만 타입 매개변수로 지정할 수 있다.**   
super는 **T와 T의 상위 클래스만 타입 매개변수로 지정할 수 있다.**   
