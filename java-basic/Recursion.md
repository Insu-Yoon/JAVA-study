# 재귀(Recursion)

어떤 문제를 같은 구조의 더 작은 문제로 나누고, 작은 문제를 해결함으로써 전체 문제를 해결하는 방법을 재귀라고 한다. 
자바에서는 메서드 안에서 메서드 자신을 호출하거나 반환하는 등의 형태를 통해 구현한다.

> **재귀로 문제를 푸는 과정**

1.  우선 문제를 재귀로 풀기 위해선 문제를 작은 단위로 쪼갤 수 있어야 하며, 재귀 호출이 종료되는 시점이 존재해야 한다. 해당 문제가 이 조건에 부합하는지 확인하자.
2.  문제를 작게 쪼갠다.  
    \- 예를 들어 입력된 배열의 모든 요소들의 합을 구하는 문제를 재귀로 풀이한다면, 각 숫자를 더하는 하나하나의 동작으로 쪼갤 수 있을 것이다  
    \- 더 이상 쪼갤 수 없는 단위(base case)까지 쪼갰다면 이 과정을 끝낸다.
3.  base case에 대해 문제를 해결한다.  
    \- 위에서 정한 예시의 경우 배열이 입력으로 주어지므로, 입력이 빈 배열이 되는 경우가 base case에 해당할 것이다.  
    그럼 if(arr.length==0) return 0; 과 같은 형태로 문제를 해결할 수 있다.
4.  그럼 쪼개졌던 문제들이 거슬러 올라가며 합쳐져 답을 구하게 된다.

배열이 입력되었을 때, 배열의 모든 요소의 합을 구하는 메서드를 아래와 같이 구현할 수 있다.

```java
import java.util.Arrays;

public class recursive {
    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,5};//배열의 모든 요소의 합을 재귀로 구하자
        System.out.println(arrSum(arr));
    }
    public static int arrSum(int[] arr) {
        if (arr.length == 0) return 0;
        int[] tail = Arrays.copyOfRange(arr, 1, arr.length);
        //arr의 첫 요소를 제외한 나머지를 갖는 tail
        return arr[0] + arrSum(tail);
    }
}
```

arrSum() 메서드를 보면 반환값으로 arr\[0\]이라는 정수 하나와 arrSum(tail)을 갖는다.   
반환값으로 메서드를 갖는다는 것은 해당 메서드를 호출하여, tail이라는 매개 변수를 입력한 결과를 반환하라는 것이다.  
따라서 arrSum() 메서드는 다음과 같이 표현할 수 있다.  

```java
public static int arrSum(int[] arr) {
    if (arr.length == 0) return 0;
    int[] tail = Arrays.copyOfRange(arr, 1, arr.length);
    //arr의 첫 요소를 제외한 나머지를 갖는 tail
    return arr[0] + arr[1] + arrSum(Arrays.copyOfRange(tail, 1, tail.length));
}
```

> **재귀의 동작 방식을 설명하기 위한 코드이지, 실제로 동작하는 코드는 아니다.**

위처럼 계속해서 arrSum 자기자신을 반복적으로 호출할 것이며, 이러한 호출은 메서드 내에서 지정한 '특정 조건'을 만족해야 멈추게 된다.   
위 코드에서 지정한 조건은 'arr의 길이가 0이면 0을 반환한다'이며, 이것을 탈출 조건이라고 한다.    
만약 재귀호출을 계속 반복하는데 이 탈출 조건을 만족하지 못하면 StackOverFlow error가 발생한다. 이는 해당 메서드가 무한루프에 빠졌다고 생각하면 되겠다.

그럼 위 메서드가 어떤 방식으로 동작하고 탈출 조건을 만족해 결과값을 내놓는지 예시를 통해 살펴보자

```java
public static int arrSum(int[] arr) { // arr = {1,2,3,4,5};
    if (arr.length == 0) return 0;
    //arr의 길이는 5이므로, 조건을 만족하지 않는다.
    int[] tail = Arrays.copyOfRange(arr, 1, arr.length);
    //tail = {2,3,4,5}
    return arr[0] + arrSum(tail);
    // 1 + arrSum({2,3,4,5});
}
```

입력으로 {1,2,3,4,5}라는 배열이 주어졌을 때, arrSum()의 반환은 1 + arrSum(tail) 이며, tail은 {2,3,4,5} 이다.

그럼 arrSum(tail)의 흐름은 아래와 같다.

```java
public static int arrSum(int[] arr) { // arr = {2,3,4,5};
    if (arr.length == 0) return 0;
    //arr의 길이는 4이므로, 조건을 만족하지 않는다.
    int[] tail = Arrays.copyOfRange(arr, 1, arr.length);
    //tail = {3,4,5}
    return arr[0] + arrSum(tail);
    // 2 + arrSum({3,4,5});
}
```

이렇게 재귀 호출이 반복되고, 결국엔 아래와 같은 형태가 될 것이다.

```java
public static int arrSum(int[] arr) { // arr = {5};
    if (arr.length == 0) return 0;
    //arr의 길이는 4이므로, 조건을 만족하지 않는다.
    int[] tail = Arrays.copyOfRange(arr, 1, arr.length);
    //tail = {}
    return arr[0] + arrSum(tail);
    // 5 + arrSum({});
}
```

입력된 배열이 {5}일 때, 메서드는 5 + arrSum(tail) 이라는 반환값을 갖는다.   
빈 배열을 입력받았을 때, if문을 통해 0을 리턴하게 되므로 arrSum({})은 0으로 치환할 수 있다.    
그럼 이제 재귀 호출은 종료되고, 모든 값이 정해졌다. 과정을 다시 한 번 정리해보자.

```java
arrSum({1,2,3,4,5}) == 1 + arrSum({2,3,4,5})
== 1 + 2 + arrSum({3,4,5}) ... == 1+2+3+4+5 +arrSum({})
 arr.length 가 0이면 0을 리턴하므로, 최종 리턴은 1+2+3+4+5+0;
```

---

메서드 내에서 자기 자신을 호출함으로써 입력에 대한 처리를 수행하는 재귀에 대해 알아보았다.   
그럼 재귀함수의 장, 단점은 무엇일까?

## 재귀 함수의 장점

-   여러 개의 반복문을 사용하지 않기 때문에, 코드가 비교적 간결하며 수정이 용이하다.  
    \- 위 예시에서는 반복문 하나로 충분히 처리할 수 있는 메서드였기 때문에 해당 장점이 드러나지 않지만 2개, 3개, 혹은 그 이상의 반복문이 중첩될 시 재귀를 통해 훨씬 간결한 코드를 작성할 수 있다.
-   변수를 여러개 사용할 필요가 없다.  
    \- 만약 반복문이 중첩되면 여러 개의 변수를 선언해가며 코드를 작성해야한다. 재귀는 이를 해결할 수 있다.

## 재귀 함수의 단점

-   코드의 흐름을 직관적으로 파악하기 어렵다.  
    \- 간단한 코드의 경우엔 크게 문제가 없겠지만, 조금만 구성이 복잡해져도 재귀 호출을 통해 어떤 동작이 발생하는지 직관적으로 파악하기 힘들 것이다.
-   메모리를 많이 사용한다.  
    \- 재귀 호출을 반복하는 동안 모든 지역변수, 매개변수, 반환값을 process stack에 저장한다. 따라서 반복문을 통해 작성한 코드보다 많은 메모리를 사용하게 된다.
-   메서드 종료 이후 복귀를 위한 Context Switching 비용이 발생한다.

요약하면 비교적 간결하고 수정이 쉬운 코드를 작성할 수 있지만, 작성된 코드는 직관성이 떨어지고 경제적이지 못하다고 할 수 있겠다.  
하지만 분할 정복 알고리즘(Divide-and-conquer algorithm)과 관련된 예제를 이해하고 풀이하기 위해서는, 재귀에 대한 이해가 필수라고하니, 잘 이해해 둘 필요가 있겠다.
