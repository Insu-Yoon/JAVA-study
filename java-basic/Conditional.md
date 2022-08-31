# **조건문(Conditional)**
설정한 조건에 따라 특정한 동작을 하도록 설정하는 문장이다. 조건에 대해 true인지 false인지에 따라 다른 동작을 지정할 수 있다.
***

## if문의 형태

```java
if(condition){
  statement1;
  }
  else{statement2;}
```
(condition)에서 boolean의 리터럴이 true면 statement1을, false면 statement2를 실행한다.

***

## switch문의 형태

```java
switch(expression){
  case value1:
    statement1;
    break;
  case value2:
    statement2;
    break;
  case value3:
    statement3;
    break;
  default:
    default_statement;
    break;
  }
  ```
  (expression)의 값에 따라 case를 나누고, 그에 따른 statement를 지정한다. 모든 case에 해당하지 않을 시 default_statement를 실행한다.
