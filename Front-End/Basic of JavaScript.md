# JavaScript
HTML, CSS와 함께 웹을 구성하는 요소 중 하나. 웹 페이지의 동작을 담당

## 1. JS의 변수 타입
- 기본적으로 null, undefined, number, bigint, string, boolean 타입이 있음
  - float, double 이딴거 없음. 그냥 number로 퉁침. bigint도 굳이? 느낌이라고 함
- Object를 제외한 모든 타입은(위에서 나열한 모든 타입) Primitive value를 가짐
- 타입은 존재하지만, 변수 선언 시 "변수형 변수명 = 초기값"의 형태로 선언하지않음
- 변수(상수) 선언은 let, const로 실행
  - var도 존재하지만, 이건 const let으로 나눠지기 전 구시대 잔재. 그냥 안쓰는게 좋음
- 변수에 대해 초기화를 하지않고 선언시 undefined("값이 없음"을 의미)
- null은 "객체가 없음"을 의미

## 2. 변수 선언(const, let)
### const
const(constant)로 선언할 경우 값 변경이 불가능함. 엄밀히 말하면 'const로 선언한 객체 자체'를 변경하는게 불가능함
```js
//Object를 제외한 모든 타입이 primitive type이므로, 기본적으로 const로 선언된 변수는 변경불가
const numberA = 12;
const stringB = "lorem";

numberA = 13;
//TypeError: Assignment to constant variable
stringB = "new string";
//TypeError: Assignment to constant variable
```
위에서 설명했듯, Object를 제외한 모든 타입은 primitive이므로 상수로 선언한 경우 변경 시도 시 타입 에러를 띄움   
하지만 Object는 const여도, 해당 객체 자체를 갈아치우려고 하는 게 아니면 변경 가능
```js
const array = [0, 1, "hello", true];

array = [1, 3, "bye", false];
//TypeError: Assignment to constant variable.
array[0] = 3;
//array = [3, 3, "bye", false]
```
코드처럼 내부의 값을 추가, 삭제, 변경하는 것은 가능함(객체의 속성을 바꾸는 건 가능)   
### let
'이건 나중에 값 바꿔가면서 쓸거임' 싶은건 let으로 선언   
선언 자체가 타입같은걸 정의하는 게 아니라서, const는 제약이 존재한다는 점 말고는 다를게 없음

## 3. Object 개념
특정 key와 value가 묶여있는 객체 개념. 브라우저에서 던져주는 것도 있고, 직접 만들수도 있음  
Java의 Class - Object 개념이랑 비슷하게 생각하면 될 듯   
차이가 있다면 JS엔 클래스 개념이 Prototype을 문법적으로 class로 보이게 했을뿐이다 정도?
```js
const player = {
  name : "ingsu",
  points : 10;
}
```
위처럼 만들면 하나의 객체가 됨. player.name 찍으면 "ingsu"나오고 points 찍으면 10 나옴   

> 기존 player에는 없는 속성인 level에 대해 player.level = 1 이라고 선언하면 어떻게 될까?

**JS에서는 player에 새로운 key로 level을 추가하고, 해당 key의 value에 1을 할당함**

## 4. function
함수. 동작하려면 있어야겠지
```js
function sayHello(){
  alert("Hello!!!!!!!!!!!")
}

//만약 Object안에서 정의할 경우 아래처럼
const player ={
  name : "ingsu",
  points : 10;
  sayHello: function(){
  alert("Hello!!!!!!!!!!!")
  }
}
```
