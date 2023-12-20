# Flexbox
요소들을 자동으로 정렬해주고, 박스를 유연하게 늘리거나 줄이면서 레이아웃을 만들 수 있음
`display : flex`

> Flexbox는 항상 부모 요소에 속성을 지정하여 자식 요소들을 움직이게 함   
> Main axis와 Cross axis는 항상 고정된 건 아님

## 부모 요소에 적용해야할 Flexbox 속성
- flex-direction : 정렬할 방향 지정
  - row : 가로정렬
  - column : 세로정렬
  - row-reverse, column-reverse : 가로/세로 반대로

 
- flex-wrap : 개행 설정
  - no-wrap : 기본값. 박스 안의 요소의 크기가 커지면 박스를 벗어남
  - wrap : 박스 크기에 맞춰 개행
  - wrap-reverse : 반대로 개행(문장이라면, 앞 문장이 아래로 내려감)

 
- justify-content : 축 수평 방향 정렬(일반적으로 가로방향 정렬)
  - flex-start : 왼쪽으로 정렬
  - flex-end : 오른쪽으로 정렬
  - center : 가운데 정렬
  - space-between : 요소들 사이에 동일 간격으로 정렬(ㅁ-ㅁ-ㅁ) - 양끝엔 따로 간격 x
  - space-around : 요소들 양쪽에 동일한 간격을 두고 정렬(-ㅁ--ㅁ--ㅁ-) - 양쪽에 같은 크기의 마진이 있는 형태로 정렬된다고 생각(간격 조정에 있어 마진은 무시됨)


- aliggn-items : 축 수직 방향 정렬(일반적으로 세로방향 정렬)
  - stretch : 요소들을 컨테이너에 맞게 늘림
  - flex-start : 위쪽으로
  - flex-end : 아래로
  - center : 가운데
  - baseline : 컨테이너의 시작위치에 정렬

 
## 자식 요소에 적용하는 Flexbox 속성
- flex-grow : 빈 공간이 있을 때, 자식 요소의 크기가 얼마나 늘어나서 공간을 차지할 것인지 비율 정하는 속성
- flex-shrink : 요소의 크기가 줄어야할 때 얼마나 줄어들 것인지 정하는 속성(0으로 두면 아이템의 크기가 flex-basis 보다 작아지지 않음)
- flex-basis : 위 속성들에 영향받기 전에 요소들이 가지는 기본크기



---
코드 예시
```html
<div class="flex-container">
        <div class="flex-item">1</div>
        <div class="flex-item">2</div>
        <div class="flex-item">3</div>
</div>
```
```css
.flex-container{
    display: flex;
    width: 700px;.
    justify-content: space-between;
    flex-wrap: wrap;
    flex-direction: column;
    align-items:
}
.flex-item{
    width: 20%;
    height: 100px;    
}
```
