# Array
배열 : 동일한 type의 변수의 묶음   
* 1차원배열 : 단순한 배열
```java
int[]array = new int[num];
array = {0, 1, 2, 3, 4};
```
변수와 마찬가지로 선언과 초기화, 할당이 한번에 가능하며 element의 갯수(num)을 생략할 수 있다.
```java
int[]array = new int[]{0, 1, 2, 3, 4};
```

* 2차원배열 : 배열 안에 배열
```java
int[][] array = new int[2][4];
```
위에서 선언한 array의 형태는 아래와 같다.
```java
{
   {0, 0, 0, 0},
   {0, 0, 0, 0}
}
```
선언 후 int형으로 초기화 하였기 때문에 기본적으로 array는 0으로 채워진다.   
   
* 가변배열 : 2차원 이상의 배열은 마지막 차수에 해당하는 배열의 길이를 고정하지 않아도 된다. 이러한 배열을 가변배열이라고 한다.
```java
int[][] array = new int[3][];
```
위와 같이 선언한 경우 내부 array의 갯수는 3개(외부 array의 길이가 3)이지만 내부 array의 길이는 입력에 따라 다음과 같이 유동적으로 변할 수 있다.
```java
{
  {0, 1, 2, 3, 4},
  {a, b},
  {orange, apple, pineapple}
}
```
***
* Arrays.copyOfRange(arr,index1,index2) : array를 index를 정해 복사할 수 있다.
```java
int[] arr1 = new int(0, 1, 2, 3, 4};
int[] arr2 = Arrays.copyOfRange(arr1, 2, 4);
//arr2 = {2, 3}
```
주의) 마지막 인자로 들어가는 index까지 복사하는 것이 아니라 해당 index이전까지만 복사한다.   
* System.arraycopy(arr1, index1, arr2, index2, length) : 각 array의 지정된 index부터 length만큼 복사한다.
```java
int[] arr1 = new int[]{0, 1, 2, 3, 4, 5, 6};
int[] arr2 = new int[10];
System.arraycopy(arr1, 2, arr2, 4, 3);
//arr2 = {0, 0, 0, 0, 2, 3, 4, 0, 0, 0};
```
* str.split(regex) : regex로 받은 문자열 패턴을 기준으로 문자열을 나눈다.
```java
String str = "hello i am a beginner";
String[] result = str.split(" ");
//result = [hello, i, am, a, beginner]
```
