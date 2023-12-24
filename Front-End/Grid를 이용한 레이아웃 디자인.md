# Grid
격자를 만들고 거기에 배치해 넣는 방식으로 레이아웃을 설계하는 방법

## 방법
바로 예시코드
```css
.grid-container {
  display: grid;
  grid-template-columns: 100px 100px 100px;
  grid-template-rows: 100px 100px;
  div {
    border: 1px solid black;
  }
}
```
참고로 위 css를 적용할 html 코드는 이렇게 생김
```html
<div class="grid-container")>
  <div></div>
  <div></div>
  <div></div>
  <div></div>
  <div></div>
  <div></div>
</div>
```
그냥 grid-container안에 div 6개

그럼 css에 의해서 스타일링 된 결과물은 
 div | div | div 
-----|-----|-----
 **div** | **div** | **div** 

이렇게 생김. 단위를 100px로 해줬으니 실제로는 정사각형 6개가 2x3으로 붙어있는 모양이겠지   
여기서 100px 같은 단위 말고 `fr`이라는 단위를 써도 됨   
1fr 1fr 1fr 으로 하면 전체 너비를 3등분해서 1칸이 전체 너비의 1/3이 됨   
1fr 2fr 1fr 이런식으로 짜면 4등분하고 1 : 2 : 1 비율로 차지함
근데 이거 자체는 결국 '격자만들기용' 이기 때문에 굳이?

## 레이아웃 만들기 #방법1
```html
<div class="grid-container">
  <div class="grid-nav">헤더</div>
  <div class="grid-sidebar">사이드바</div>
  <div class="grid-content">콘텐츠</div>
</div>
```
대충 위와 같은 구성일 때, navbar를 최상단 1줄을 모두 차지하게 레이아웃을 짜고싶다?
```css
.grid-container {
  display: grid;
  grid-template-columns: 100px 100px 100px;
  grid-template-rows: 100px 100px 100px;
  grid-gap: 10px;
}

.grid-nav {
  grid-column : 1 / 4;
  grid-row : 1 / 2;
}
```
이렇게 설계하면 됨. 저기서 `1 / 4`, `1 / 2`가 뜻하는 바는 "첫번째 세로선 ~ 네번째 세로선까지를 영역으로 한다", "첫번째 가로선 ~ 두번째 가로선까지를 영역으로 한다" 임   
위에서 grid-container를 3x3으로 만들어 놨기 때문에 가로선, 세로선은 각각 4개가 존재함   
그 중 1~4(첫번째 열~세번째 열), 1~2(첫번째 행만) 을 nav의 영역으로 하게되는 것   

## 레이아웃 만들기 #방법2
#방법1과 같은 html에 같은 레이아웃으로 nav를 만들고 싶다면
```css
.grid-nav {
  grid-area: 헤더;
}

.grid-sidebar {
  grid-area: 사이드;
}
```
자식요소에는 이렇게 이름만 지정하고, 부모요소에서 칸을 할당하는 방법이 있음
```css
.grid-container {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr;
  grid-template-rows: 100px 100px 100px;
  grid-gap: 10px;
  grid-template-areas: 
    "헤더 헤더 헤더 헤더"
    "사이드 사이드 . ."
    "사이드 사이드 . ."
}
```
저렇게 자식요소 이름을 칸에 넣는다는 느낌으로 적어넣으면 레이아웃이 짜잔하고 만들어짐

간단한 레이아웃을 짤때라면 두 방법중 편한대로 골라서 쓰면 될듯
