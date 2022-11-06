# **N:1(다대일) 연관 관계 매핑**

```java
@ManyToOne
@JoinColumn(name = "MEMBER_ID")
private Member member;

public void addMember(Member member){
	this.member = member;
}
```

주문과 회원의 관계에서, 한 명의 회원은 여러 개의 주문을 가질 수 있지만, 주문 하나는 여러 명의 회원을 요소로 가질 수 없다. Order 클래스에서 위와 같이 선언하여, N:1 관계를 구현할 수 있다.

-   name = "외래키의 컬럼명"

# **1:N(일대다) 연관 관계 매핑**

```java
@OneToMany(mappedBy = "member")
private List<Order> orders = new ArrayList<>();

public void addOrder(Order order){
	orders.add(order);
}
```

만약 필요하다면, Order 클래스에서도 Member 클래스와의 연관 관계를 명시함으로써, Member의 요소를 참조할 수 있다.

-   mappedBy = "상대 엔티티가 참조하고 있는 내 객체(외래키로 넘겨준 객체 이름)"

> **N:1이든, 1:N이든 외래키를 중심으로 생각하자**

# 1:1, N:N은 비교적 잘 사용하지 않는다.

-   1:1일 경우, 필드에 포함하는 것이 나은 경우가 많다.
    -   하지만 로직에 의해 필요할 경우 @OneToOne 애너테이션을 통해 관계를 정의할 수 있다.
    -   애너테이션이 다르다는 것 외에는 선언 방법이 @ManyToOne과 똑같다.
    -   @ManyToMany의 경우 불필요한 데이터가 들어가거나, 데이터 삭제 시 원하지 않는 삭제가 일어날 수 있어 N:1, 1:N으로 분리하여 사용하는 것이 권장된다.
