# Thread
우리가 프로그램을 실행하면 프로그램은 운영체제로부터 메모리를 할당받고 프로그램이 실행되는데, 이렇게 실행중인 프로그램을 '프로세스'라고 한다. 이때 프로세스 내에서 실행되는 코드의 흐름을 '스레드'라고 한다.

어떠한 프로세스가 스레드를 하나만 사용하면 '싱글 스레드 프로세스'이고, 여러개의 스레드를 동시에 사용하면 '멀티 스레드 프로세스'일 것이다. 

---

## **스레드(Thread)**

위에서 간단히 소개했듯 프로세스는 데이터, 컴퓨터 자원, 스레드로 구성되는데, 프로세스가 할당받은 자원과 데이터를 활용해 소스 코드를 실행하는 것이 스레드이며, 스레드는 소스 코드의 흐름이라고 할 수 있다.

## **메인 스레드(Main Thread)와 멀티 스레드(Multi-Thread)**

자바 어플리케이션을 실행하면 가장 먼저 실행되는 메서드는 main 메서드이며, 이 메서드를 실행하는 것이 메인 스레드이다.

![image](https://user-images.githubusercontent.com/110891599/190639929-0291596b-65ce-4264-a535-bdf2bec238c6.png)


프로세스가 메인 스레드 하나만 가질 경우 싱글 스레드 프로세스, 작업 스레드를 추가로 더 가질 경우 멀티 스레드 프로세스라고 한다. 스레드를 여러 개 가진다는 것은 여러 스레드가 동시에 작업을 수행할 수 있음을 의미한다. 이렇게 동시에 작업을 수행하는 것을 '멀티 스레딩'이라고 부른다.

그렇다면 자바에서는 멀티 스레딩을 어떻게 구현할까?

-   작업 스레드가 수행할 동작을 코드로 작성한다
-   작업 스레드를 생성하여 실행한다

위의 작업을 통해 특정 동작을 수행할 작업 스레드를 생성하고 실행하여 멀티스레딩을 구현할 수 있다.

자바에서는 스레드가 수행할 동작을 run() 메서드를 통해 작성하도록 규정되어있는데, 이 메서드는 Runnable 인터페이스와 Thread 클래스에 정의되어있다.

따라서 자바에서는  Runnable 인터페이스나 Thread 클래스를 사용하여 스레드를 생성한다.

---

###   **Runnable 인터페이스를 사용해 스레드 생성**

우선 Runnable 인터페이스를 implement하는 Class를 만들고, 해당 클래스에서 Runnable의 run() 메서드를 오버라이딩하여 동작을 정의한다.

```java
class Thread1 implements Runnable{
    @Override
    public void run() {
        System.out.println("Runnable을 implement한 클래스");
    }
}
```

그런 다음, main 메서드에서 스레드를 생성하고 start() 메서드를 통해 스레드를 실행시킨다.

```java
public class Main {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new Thread1());
        thread1.start();
    }
}
```

###  **Thread 클래스를 사용해 스레드 생성**

클래스를 만들고, run()을 오버라이딩 하는 것 까지 똑같다. 다른것은 Thread 클래스를 상속한다는 것 뿐이다.

```java
class Thread2 extends Thread{
    @Override
    public void run() {
        System.out.println("Thread를 extend한 클래스");
    }
}
```

클래스를 상속받아 run()을 구현한 경우 main에서 Thread형이 아니라, Thread2 를 참조 변수 타입으로 하여 생성할 수 있다.

```java
public class Main {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new Thread1());
        thread1.start();
        Thread2 thread2 = new Thread2();
        thread2.start();
    }
}
```

> **참고로, Runnable 인터페이스를 구현한 클래스를 같은 방법으로 인스턴스화하면 start() 메서드를 사용할 수 없다.**  
> **Runnable 인터페이스에는 run()메서드만 선언되어있기 때문이다.**

위의 코드를 실행하면 thread1과 thread2가 각각 run()에 지정된 동작을 수행한다.

멀티스레딩을 확인하기 위해, 각각의 run()메서드에 for문을 추가해서 반복적으로 출력하게 구성한 후 결과를 보겠다.

```java
  Runnable을 implement한 클래스
  Runnable을 implement한 클래스
  Thread를 extend한 클래스
  Thread를 extend한 클래스
  Thread를 extend한 클래스
  Thread를 extend한 클래스
  Thread를 extend한 클래스
  Runnable을 implement한 클래스
  Runnable을 implement한 클래스
  Runnable을 implement한 클래스
```
각각의 출력이 정해진 순서없이 섞여서 나오는 것을 확인할 수 있다.

필요할 경우, 각각의 익명 객체를 활용하여 스레드를 생성할 수도 있다.

```java
Thread thread1 = new Thread(new Runnable() {
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.print("익명 객체 Runnable");
        }
    }
});
```

\* 닫는 소괄호 ')'와 세미콜론 ';'의 위치를 유심히보자. 저기까지가 new Runnable()을 선언하는 부분이다.

```java
Thread thread2 = new Thread() {
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.print("익명 객체 Thread");
        }
    }
};
```

\* 여기서도 세미콜론 ';'의 위치를 확인해두자. 마찬가지로 저기까지가 new Thread(){ ---}; 의 선언부이다.

---

##  **스레드 관련 메서드들**

-   start() : 스레드를 실행대기 상태로 만든다.  
    (엄밀히 말하면 start()를 입력하는 순간 스레드가 실행되는 것이 아니라, 운영체제에 의해 실행되는 순간이 결정된다.)
-   getName() : 스레드의 이름을 반환한다. 기본적으로 thread-n 으로 되어있다.(n은 정수)
-   setName() : 스레드의 이름을 변경한다.
-   currentThread() : 현재 실행중인 스레드의 주소값을 반환한다.  
    활용 => Thread.currentThread().setName() 을 통해 현재 실행중인 스레드의 이름을 변경할 수 있다.
-   sleep(long L) : L millisecond 만큼 스레드를 TIMED\_WAITING 상태로 전환시킨다.  
    (예외처리를 위해 try-catch문으로 사용한다)
-   interrupt() : sleep(), wait(), join() 등에 의해 WATING상태인 스레드들을 실행대기 상태로 복귀시킨다.
-   yield() : 다른 스레드에게 실행을 양보한다.
-   join() : 다른 스레드의 작업이 끝날 때까지 기다린다.  
    ex) thread1.join() == 현재 스레드를 WATING상태로 만들고, thread1의 실행이 끝나면 실행 대기상태로 복귀시킨다.)
-   wait(), notify() : 각 스레드에서 아래와 같이 선언되며, thread1이 작업을 완료하면 notify()를 호출하여 thread2를 runnable상태로 만들고, 자신은 wait() 메서드를 통해 waiting 상태가 된다. 마찬가지로 thread2가 작업을 완료하면  notify()를 호출하여 thread1을 runnable 상태로 만들고, 자신은 waiting 상태가 된다.

```java
class Task {
    public synchronized void method1() {
        System.out.println("Thread1의 method1 Working");
        notify();
        try { wait(); } catch(Exception e) {}
    }

    public synchronized void method2() {
        System.out.println("Thread2의 method2 Working");
        notify();
        try { wait(); } catch(Exception e) {}
    }
}
```

---

## **스레드 동기화(synchronizing)**

멀티 스레딩을 통해 여러 스레드는 동시에 실행된다는 것을 확인했다. 그렇다면 각각의 스레드가 동시에 같은 데이터에 접근하여 데이터를 변경한다면 어떻게 될까?

```java
public class Synchronizing {
    public static void main(String[] args) {
        Runnable task = new Task();
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.setName("철수");
        thread2.setName("영희");

        thread1.start();
        thread2.start();
    }
}
class Account {
    private int balance = 1000;
    public int getBalance() {
        return balance;
    }
    public boolean withdraw(int money) {
        if (balance >= money) {
            try { Thread.sleep(1000); } catch (Exception error) {}
            balance -= money;
            return true;
        }
        return false;
    }
}
class Task extends Thread {
    Account account = new Account();
    public void run() {
        while (account.getBalance() > 0) {
            int money = (int)(Math.random() * 3 + 1) * 100;
            boolean denied = !account.withdraw(money);
            System.out.println(String.format("%s가 %d원을 인출함. 잔고 : %d %s",
                Thread.currentThread().getName(), money, account.getBalance(), denied ? "-> 거절됨" : "")
            );
        }
    }
}
```

철수와 영희가 계좌에서 돈을 인출한다. 100~300원을 랜덤하게 인출하며, 잔고보다 인출하려는 금액이 크면 거절된다. 위 코드의 실행 결과는 다음과 같다.

```java
영희가 100원을 인출함. 잔고 : 700
철수가 300원을 인출함. 잔고 : 700
영희가 300원을 인출함. 잔고 : 200
철수가 200원을 인출함. 잔고 : 200
영희가 100원을 인출함. 잔고 : 100
철수가 100원을 인출함. 잔고 : 100
영희가 100원을 인출함. 잔고 : 0
철수가 100원을 인출함. 잔고 : -100
```

인출한 금액과 잔고가 안맞는 건 물론이고, 잔고보다 인출하려는 금액이 크면 거절해야하는데 마이너스 통장을 뚫어버린다. try{ Thread.sleep()} 에 의해 각각의 스레드가 조건문을 통과하는 것과, 그 아래의 메서드를 실행하는 데 시간차이가 있기 때문에 마이너스 통장까지 가는 것이지만, 해당 지연을 제거하더라도 인출 금액과 잔고는 일치하지 않는다.

> **잔고에 해당하는 balance 변수에 두 스레드가 동시에 접근하여 수정해버리기 때문에 발생하는 문제다.**

이를 해결하기 위해 해당 메서드 전체 혹은 원하는 부분에 synchronized를 선언하여 동기화함으로써 문제를 해결할 수 있다.

```java
public synchronized boolean withdraw(int money) {
    if (balance >= money) {
        try { Thread.sleep(1000); } catch (Exception error) {}
        balance -= money;
        return true;
    }
    return false;
}
```

수정된 withdraw 메서드는 위와 같다. 코드의 실행 결과는 다음과 같다.

```java
철수가 100원을 인출함. 잔고 : 900
영희가 200원을 인출함. 잔고 : 700
철수가 300원을 인출함. 잔고 : 400
영희가 100원을 인출함. 잔고 : 300
영희가 300원을 인출함. 잔고 : 100 -> 거절됨
철수가 200원을 인출함. 잔고 : 100
철수가 200원을 인출함. 잔고 : 0 -> 거절됨
영희가 100원을 인출함. 잔고 : 0
```

스레드가 withdraw 메서드에 대해 동기화되어 정상적인 동작을 하고 있다.

그렇다면 이러한 메서드 동기화의 원리는 어떻게 될까?

### **임계 영역(Critical section)과 락(Lock)**

임계 영역은 synchronized로 선언된 "단 하나의 스레드만 코드를 실행할 수 있는 영역"을 의미하며, 락은 임계영역에 접근할 수 있도록 해주는 권한을 의미한다.

즉, 스레드가 임계 영역을 만나면 다음과 같은 과정에 따라 코드가 실행된다.

1.  스레드 A가 임계 영역에 접근한다
2.  현재 임계 영역의 코드를 실행중인 스레드가 없다면, 스레드 A가 락을 획득한다
3.  스레드 A가 임계 영역의 코드를 실행한다  
    이때, 스레드 B가 임계 영역에 접근하더라도 락이 없기 때문에 코드를 실행할 수 없다
4.  스레드 A가 코드의 실행을 완료하고 락을 반납한다
5.  스레드 B가 락을 획득하여 임계 영역의 코드를 실행한다
