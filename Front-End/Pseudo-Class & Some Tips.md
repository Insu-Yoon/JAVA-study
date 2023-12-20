# pseudo-class 
각 요소들에 상태에 따른 스타일링을 지정하기위해 사용
- hover : 마우스 올려놨을때
- focus : 해당 요소가 활성화(?)된 상태(input type text의 경우 커서가 활성화된 상태 등)
- active : 버튼의 경우 클릭을 누르고 있는 상태(일반적으로 링크, 버튼에 사용)
> 이 속성들은 여러개 배치할 시 순서대로 배치해야 제대로 작동(hover-focus-active순)

## 예시코드
```css
.custom-button:hover{ 마우스를 올려놨을 때 버튼 스타일링}
.custom-textbox:focus{ input type=text 텍스트 박스안에 커서가 활성화중일 때 스타일링 }
.custom-link:link{ 방문하지 않은 링크 스타일링 }
.custom-link:visited{ 이미 방문한 링크 스타일링 }
```


기타 기록해둘만한 psuedo class들
```css
:any-link /*방문 전, 방문 후 링크 한번에 선택할 때*/
:playing /*동영상, 음성이 재생중일 때*/
:paused /*동영상, 음성이 정지시*/
:autofill /*input의 자동채우기 스타일*/
:disabled /*disabled된 요소 스타일*/
:checked /*체크박스나 라디오버튼 체크되었을 때*/
:blank /*input이 비었을 때*/
:valid /*이메일 input 등에 이메일 형식이 맞을 경우*/
:invalid /*이메일 input 등에 이메일 형식이 맞지 않을 경우*/
:required /*필수로 입력해야할 input의 스타일*/
:nth-child(n) /*n번째 자식 선택*/
:first-child /*첫째 자식 선택*/
:last-child /*마지막 자식 선택*/
```

# 생산성 향상을 위한 팁들
## Object Oriented CSS
모든 요소에 클래스를 하나하나 다 지정하는건 비효율적이다.  
따라서 공통적으로 가질만한 뼈대와 거기에 붙일 살을 나눈다고 생각하고 속성을 부여할 수 있다.  

예시 코드
```css
/*html의 코드 : class = "main-btn bg-red" */
.main-btn{ 버튼의 뼈대(패딩, 폰트, 보더, 커서 등}
.bg-red{ 배경색-red }
.bg-blue{ 배경색 - blue }
/*위와 같이 뼈대/살을 나눔으로써 코드를 재사용 할 수 있음*/
```
## BEM 네이밍 팁
Block__Elemnet--Modifier
class = "덩어리이름__역할--세부특징"
ex) profile__button bg-red


## 생산성 향상?
- 중복 스타일 재사용 가능
- 유지보수 편해짐(여러개 다 안고쳐도 됨)
- 코드 작성이 빨라짐

따라서 팁들을 찾아보고 적당히 효율 높은 걸 선택해서 적용하자

> 사실 요즘은 react, vue로 html 만드는데, 그럼 사실 oocss bem 굳이 안써도 된다고 함
