# Transform
one-way 애니메이션은 a->b transition 써서 만들었음. 하지만 a->b->c, a->b->a, a->딜레이->b 이런 애니메이션은 transition만으로는 만들 수 없음   
하지만 transform 사용하면 가능한 범위가 확 넓어짐

## 어떤 동작들이 가능할까?
```css
@keyframes 동작명{
  transform : rotate(n deg)
  /* n도만큼 회전 */
  transform : translateX(n px)
  /* n 만큼 x축 방향으로 이동 */
  transform : translateY(n px)
  /* n 만큼 y축 방향으로 이동 */
  transform : scale(비율)
  /* 정한 비율로 크기 변화 */
}
```
이 외에도 translateZ, matrix, perspecitive 등 이거저거 많음

## transform의 장점
일단 성능이 좋음. 브라우저가 화면을 그릴땐 다음과 같은 과정을 거침
1. Render Tree(css 정리한 참고자료) 만듦
2. 레이아웃을 잡음
3. 정해진 레이아웃을 채움
4. Composite 처리함 (transform, opacity 등 처리)

width, margin 같은걸로 애니메이션 넣으면 레이아웃이 바뀌므로 2,3,4 다시함   
transform으로 애니메이션 넣으면 4만 함   
**따라서 처리할 것이 줄고 부담이 덜해서 성능이 좋음**   

또, transform은 별도의 쓰레드에서 처리함(브라우저는 기본적으로 쓰레드 하나만 사용함)
> 추가 : will-change 같은 옵션으로 3d animation 사기치면 성능개선 가능
특히, 자바스크립트 많으면 무조건 transform으로 한다고 생각해야


## transform을 활용한 애니메이션 예시코드
```css
&:hover {
    .ani-text {
      animation-name: 왔다갔다;
      animation-duration: 1s;
    }
  }

@keyframes 왔다갔다 {
  0% {
    transform: translateX(0px);
  }
  25% {
    transform: translateX(-100px);
  }
  75% {
    transform: translateX(100px);
  }
  100% {
    transform: translateX(0px);
  }
}
```
- animation-name 동작명
- animation-duration 애니메이션이 몇초만에 완료될지
- animation-delay 딜레이
- animation-iteration-count 반복횟수
- animation-timing-function 베지어

위에 나열한 transform 속성 여러개 지정할 땐 한줄에 다 적어야됨. 안그럼 뒤에걸로 덮어씌워짐
