# Collection Framework
### Collection Framework의 구조
![image](https://user-images.githubusercontent.com/110891599/190153714-7f76868d-8695-492a-b596-3e15be51a325.png)

컬렉션 프레임워크는 주요 인터페이스로 List, Set, Map을 제공한다.   
**이 중 List와 Set은 공통점이 많아 Collection이라는 인터페이스로 묶인다.**
* List 
  * 데이터의 순서가 유지된다
  * 중복이 허용된다
  * ArrayList, LinkedList, Vector, Stack 등
* Set 
  * 데이터의 순서가 유지되지 않는다
  * 중복이 허용되지 않는다. 
  * (Set == 집합)
  * HashSet, TreeSet 등
* Map 
  * 데이터의 순서가 유지되지 않는다
  * 데이터를 키(Key)와 값(Value)의 쌍으로 저장한다
  * value는 중복이 허용되지만 Key는 중복이 허용되지 않는다.
  * HashMap, HashTable, TreeMap 등

### Collection Framework의 메서드들

| 기능   | 반환 타입   | 메소드(매개변수) | 설명 |
|:---:|:---:|:---:|:---:|
| 객체 추가                  | boolean | add(Object o) <br> addAll(Collection c) | 주어진 객체 or 컬렉션의 객체들을 컬렉션에 추가 |
| 객체 검색 | boolean | contains(Object o) <br> containsAll(Collection c) | 주어진 객체 or 컬렉션이 저장되어 있는지 여부 반환 |
|  | Iterator | iterator() | 컬렉션의 iterator를 반환 |
|  | boolean | equals(Object o) | 컬렉션이 동일한지 여부 반환 |
|  | boolean | isEmpty() | 컬렉션이 비어있는지 여부 반환 |
|  | int | size() | 저장되어 있는 전체 객체 수를 반환 |
| 객체 삭제 | void | clear() | 컬렉션에 저장된 모든 객체를 삭제 |
|  | boolean | remove(Object o) <br> removeAll(Collection c) | 주어진 객체 및 컬렉션을 삭제하고 성공 여부를 반환 |
|  | boolean | retainAll(Collection c) | 주어진 컬렉션을 제외한 모든 객체를 삭제하고,<br> 컬렉션에 변화가 있는지의 여부를 반환 |
| 객체 변환 | Object[] | toArray() | 컬렉션에 저장된 객체를 객체배열(Object [])로 반환 |
|  | Object[] | toArray(Object[] a) | 주어진 배열에 컬렉션의 객체를 저장해서 반환 |

해당 메서드들은 Collection 인터페이스에 정의되어있으므로, Collection 인터페이스를 구현하는 List, Set 등에서도 사용할 수 있다.
***
## List<E>
List는 배열처럼 객체를 일렬로 늘어놓은 구조를 가진다. 객체를 저장하면 배열처럼 인덱스가 부여되므로, 인덱스를 가지고 객체를 다루는 메서드가 많다.
  처음에 서술했듯, List의 구현 클래스들은 공통적으로 **데이터의 순서가 유지되며, 데이터의 중복을 허용한다.**
  
| 기능   | 반환 타입   | 메소드(매개변수) | 설명 |
|:---:|:---:|:---:|:---:|
| 객체 추가                  | boolean | add(Object o) <br>addAll(Collection c) | 주어진 객체 or 컬렉션의 객체들을 컬렉션에 추가 |
| 객체 검색 | boolean | contains(Object o) <br> containsAll(Collection c) | 주어진 객체 or 컬렉션이 저장되어 있는지 여부 반환 |
|  | Iterator | iterator() | 컬렉션의 iterator를 반환 |
|  | boolean | equals(Object o) | 컬렉션이 동일한지 여부 반환 |
|  | boolean | isEmpty() | 컬렉션이 비어있는지 여부 반환 |
|  | int | size() | 저장되어 있는 전체 객체 수를 반환 |
| 객체 삭제 | void | clear() | 컬렉션에 저장된 모든 객체를 삭제 |
|  | boolean | remove(Object o) <br>removeAll(Collection c) | 주어진 객체 및 컬렉션을 삭제하고 성공 여부를 반환 |
|  | boolean | retainAll(Collection c) | 주어진 컬렉션을 제외한 모든 객체를 컬렉션에서 삭제하고,<br> 컬렉션에 변화가 있는지의 여부를 반환 |
| 객체 변환 | Object[] | toArray() | 컬렉션에 저장된 객체를 객체배열(Object [])로 반환 |
|  | Object[] | toArray(Object[] a) | 주어진 배열에 컬렉션의 객체를 저장해서 반환 |

### ArrayList
ArrayList는 컬렉션 프레임워크에서 가장 많이 사용되는 인터페이스다. 객체를 인덱스로 관리한다는 점에서 배열과 유사한 부분이 있다.   
  Array와의 차이점으로는 크기를 따로 지정하지 않아도 10의 default length를 지니며, 최대크기에 도달 후에도 데이터를 계속 add하면 기존크기의 1.5배로 확장된다.   
  특정 인덱스의 객체를 삭제하면 바로 뒤 인덱스의 객체부터 한 칸씩 앞으로 당긴다.(복사-저장) 따라서 중간 인덱스에 객체를 추가하거나, 삭제할 때 비교적 성능이 낮다.
  ```java
  ArrayList<E> list = new ArrayList<>();
  //예시
  ArrayList<String> list1 = new ArrayList<>();
  ArrayList<Integer> list2 = new ArrayList<>();
  ```
  제너릭에서 학습했듯, 기본형의 경우 타입매개변수는 Wrapper Class형으로 지정한다.
### LinkedList
  LinkedList는 데이터가 불연속적으로 존재한다. 각각의 객체는 이전 객체 및 다음 객체가 저장된 주소값과 데이터로 이루어진다.   
  객체가 추가/삭제 시 객체가 저장하는 앞,뒤 객체의 주소값만 바꾸면 되므로, 데이터의 추가/삭제에 있어 ArrayList에 비해 이점을 갖는다.   
  
  ArrayList와 LinkedList를 비교하면 다음과 같다.
  
  | 기능 | ArrayList | LinkedList|
  |:---:|:---:|:---:|
  |데이터의 순차적 추가 및 삭제| 빠름 | 느림|
  |데이터 사이의 추가 및 삭제|느림|빠름|
  |객체 탐색 |빠름|느림|
  
  따라서 데이터의 잦은 변경이 예상된다면 LinkedList를, 데이터의 개수가 변하지 않는다면 ArrayList를 사용하는 것이 유리하다.
  ***
  ## Set<E>
  수학에서의 Set은 집합을 의미한다. java에서도 Set은 집합과 비슷한 기능을 한다.
  처음에 서술했듯 Set은 데이터의 순서를 유지하지 않으며, 중복을 허용하지 않는다.
  이러한 특징은 당연히 Set의 구현클래스인 HashSet, TreeSet 등에도 적용된다.
  
  Set의 대표적 메서드들
  | 기능   | 반환 타입   | 메소드(매개변수) | 설명 |
|:---:|:---:|:---:|:---:|
| 객체 추가                  | boolean | add(Object o) <br> addAll(Collection c) | 주어진 객체 or 컬렉션의 객체들을 컬렉션에 추가 |
| 객체 검색 | boolean | contains(Object o) <br> containsAll(Collection c) | 주어진 객체 or 컬렉션이 저장되어 있는지 여부 반환 |
|  | Iterator | iterator() | 컬렉션의 iterator를 반환 |
|  | boolean | equals(Object o) | 컬렉션이 동일한지 여부 반환 |
|  | boolean | isEmpty() | 컬렉션이 비어있는지 여부 반환 |
|  | int | size() | 저장되어 있는 전체 객체 수를 반환 |
| 객체 삭제 | void | clear() | 컬렉션에 저장된 모든 객체를 삭제 |
|  | boolean | remove(Object o) <br> removeAll(Collection c) | 주어진 객체 및 컬렉션을 삭제하고 성공 여부를 반환 |
|  | boolean | retainAll(Collection c) | 주어진 컬렉션을 제외한 모든 객체를 컬렉션에서 삭제하고,<br> 컬렉션에 변화가 있는지의 여부를 반환 |
| 객체 변환 | Object[] | toArray() | 컬렉션에 저장된 객체를 객체배열(Object [])로 반환 |
|  | Object[] | toArray(Object[] a) | 주어진 배열에 컬렉션의 객체를 저장해서 반환 |
  
  ### HashSet
