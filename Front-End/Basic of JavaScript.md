# JavaScript
HTML, CSS와 함께 웹을 구성하는 요소 중 하나. 웹 페이지의 동작을 담당

## 1. JS의 변수 타입
- 기본적으로 null, undefined, number, bigint, string, boolean 타입이 있음
- Object를 제외한 모든 타입은(위에서 나열한 모든 타입) Primitive value를 가짐
- 타입은 존재하지만, 변수 선언 시 "변수형 변수명 = 초기값"의 형태로 선언하지않음
- 변수(상수) 선언은 let, const로 실행
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
