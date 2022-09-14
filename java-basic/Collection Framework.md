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
  
| 기능 | 반환 타입 | 메서드(매개변수) | 설명 |
|:---:|:---:|:---:|:---:|
| 객체 추가 | void | add(int index, Object element) | 입력된 인덱스에 객체를 추가 |
|  | boolean | addAll(int index, Collection c) | 입력된 인덱스에 컬렉션을 추가 |
|  | Object | set(int index, Object element) | 입력된 인덱스에 객체를 저장(덮어씀) |
| 객체 검색 | Object | get(int index) | 입력된 인덱스에 저장된 객체를 반환 |
|  | int | indexOf(Object o) <br> lastIndexOf(Object o) | 순방향 / 역방향으로 탐색하여 주어진 객체의 인덱스를 반환 |
|  | ListIterator | listIterator() <br> listIterator(int index) | List의 객체를 탐색할 수 있는ListIterator 반환 <br> 입력된 index부터 탐색할 수 있는 ListIterator 반환 |
|  | List | subList(int fromIndex, int toIndex) | fromIndex부터 toIndex직전까지의 객체를 반환 |
| 객체 삭제 | Object | remove(int index) | 입력된 인덱스에 저장된 객체를 삭제하고 삭제된 객체를 반환 |
|  | boolean | remove(Object o) | 입력된 객체를 삭제 |
| 객체 정렬 | void | sort(Comparator c) | 입력된 비교자(comparator)로 List를 정렬 |

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
  | 기능 | 반환 타입 | 메서드(매개변수) | 설명 |
|:---:|:---:|:---:|:---:|
| 객체 추가 | boolean | add(Object o) | 주어진 객체를 추가하고, 성공 여부를 반환 |
| 객체 검색 | boolean | contains(Object o) | 주어진 객체가 Set에 존재하는지 확인 |
|  | boolean | isEmpty() | Set이 비어있는지 확인 |
|  | Iterator | Iterator() | 저장된 객체를 하나씩 읽어오는 Iterator를 반환 |
|  | int | size() | 저장되어있는 전체 객체의 수를 반환 |
| 객체 삭제 | void | clear() | Set에 저장된 모든 객체를 삭제 |
|  | boolean | remove(Object o) | 주어진 객체를 삭제 |
  
### HashSet
HashSet은 데이터를 추가할 때, hashCode() 메서드와 equals() 메서드를 통해 중복여부를 판단한다. 그 과정은 다음과 같다.
1. add(Object O)를 입력받는다.
1. hashCode() 메서드를 통해 객체의 해쉬코드를 얻는다.
1. hashCode() 메서드를 통해 HashSet에 있는 객체들의 해쉬코드를 얻는다.
1. 2와 3의 해쉬코드를 비교해 같은 것이 없으면 Set에 추가한다.
1. 4에서 중복된 해쉬코드가 발견되었으면, 해당 두 객체를 equals() 메서드로 비교하여 다른 객체일 시 Set에 추가한다.

### TreeSet
TreeSet은 이진 탐색 트리 구조로 데이터를 저장하며, 기본적으로 오름차순으로 데이터를 정렬한다.   
이진 탐색 트리란 아래와 같은 형태를 가지며, 부모 노드가 왼쪽 노드보다 크고 오른쪽 노드보다 작게 되도록 배치한다.   
이진 탐색 트리는 정렬과 탐색에 특화된 자료구조이다.  
 
![image](https://user-images.githubusercontent.com/110891599/190176115-5c2ca64a-a549-4848-9e4d-c8878e7b63c3.png)
 ***
 ## Map<K,V>
 Map 인터페이스는 기본적으로 키(Key)와 값(Value)로 구성된 객체를 저장한다.   
 이렇게 Key, Value 쌍으로 묶인 것을 Entry객체라고 부르며, key와 value는 각각 Key 객체와 Value 객체로 저장된다.   
 Map의 경우 key는 고유한 것으로 중복을 허용하지 않지만, value는 중복을 허용한다.   
 만약 저장되는 데이터의 key가 기존의 것과 같다면 기존에 존재하는 value는 새로 저장되는 데이터의 value로 교체된다.   
    
 Map의 대표적 메서드들
| 기능 | 리턴 타입 | 메서드(매개변수) | 설명 |
|:---:|:---:|:---:|:---:|
| 객체 추가 | Object | put(Object key, Object value) | 주어진 키로 값을 저장. 해당 키가 새로운 키일 경우 null을 반환<br> 동일한 키가 있을 경우 덮어씌우고 대체되기 이전의 값을 반환 |
| 객체 검색 | boolean | containsKey(Object key) | 주어진 키가 있으면 true, 없으면 false를 반환 |
|  | boolean | containsValue(Object value) | 주어진 값이 있으면 true, 없으면 false를 반환 |
|  | Set | entrySet() | 키와 값의 쌍으로 구성된 모든 Map.Entry 객체를 Set에 담아서 반환 |
|  | Object | get(Object key) | 주어진 키에 해당하는 값을 반환 |
|  | boolean | isEmpty() | 컬렉션이 비어 있는지 확인 |
|  | Set | keySet() | 모든 키를 Set 객체에 담아서 반환 |
|  | int | size() | 저장된 Entry 객체의 총 갯수를 반환 |
|  | Collection | values() | 저장된 모든 값을 Collection에 담아서 반환 |
| 객체 삭제 | void | clear() | 모든 Map.Entry(키와 값)을 삭제 |
|  | Object | remove(Object key) | 주어진 키와 일치하는 Map.Entry를 삭제하고 값을 반환 |
    
 ### HashMap
 HashSet과 HashMap의 차이를 명확하게 알게되면 재정리 
