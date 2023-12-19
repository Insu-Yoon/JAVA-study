# Coding Apple 정리
## HTML
  - 브라우저에서 실행가능한 기본적인 마크업 언어
	- 상위태그 head / body
	- head는 상세한 설명) 메타데이터
	- body는 사용자에게 보여지는 태그들로 이루어져있음

> 정보 찾을 때 MDN 추천

> 대략적인 구조 : box 안에 item들

### box - 사용자에겐 기본적으로 안보임
- header, footer, nav, aside, main, section, article, div, span, form
- section 안에 여러개의 article(여러가지 아이템을 그룹화, 재사용 가능)
- div - 여러 아이템 묶어서 스타일링(텍스트 + 아이콘을 묶고 배경을 넣거나 할 때 사용가능)

### item - 사용자에게 보여지는것
- a, button, input, label, img, video, audio, map, canvas, table
---
### Block, Inline
**Block**
- 충분한 공간이 있어도 다음 라인에 다음 컨텐츠가 들어감 
- 라인 단위로 공간을 먹음

**Inline**
- 공간이 허용하면 다른 태그 옆에 배치가능
---
```html 
<p> Example Massage </p>
```   
Opening-tag + Content + Closing-tag      
이 전체가 하나의 Element   

```html 
<p class="editor-note"> 
```
**Attribute**
- 여러 태그들을 묶어서 관리할 수 있음                 
- attribute 중 boolean 타입인 것들은 따로 값을 지정하지 않아도 선언만 하면 적용

> 여러 태그들
- p, div, span
- ol, ul
- input, type
ol>li*3 -> 3개 항목 포함한 ordered list 생성

span 태그는 display : inline 이라는 스타일 속성 내포
따라서 폭, 높이를 단독으로 결정할 수 없음
따라서 폭, 높이 수정하려면 부모 태그에서 설정

---
# CSS
> html에 스타일까지 다 넣으면 코드가 더럽기 때문에 스타일을 따로 관리하기 위해 css 사용
```css
#special { font-size : 30px } /*아이디*/
.profile { font-size : 20px }  /*클래스*/
p { font-size : 16px } /*태그*/
```
- css 파일에서 id, 클래스, 태그에 대한 스타일 지정 가능
- 스타일에서 서로 충돌이 발생할 시 우선순위에 따라 적용
- 위 코드에서 위쪽이 더 우선순위가 높음
- 가장 우선 순위가 높은 건 html에서 직접 지정한 스타일

> 레이아웃 전체를 감싸는 wrapper(container) 박스 만들어주면 유용함 (최대 width 지정하는 정도도 ㄱㅊ)

**width : 100% -> 부모 태그의 크기의 100%**

**float 속성으로 화면 가르기 가능**  
-> float 속성 들어간 요소이 다음 요소가 float 요소의 뒤에 묻힘  
-> float 다음에 오는 요소에 
```css 
clear : left || right || both
```
써서 해결

---
```html 
<div/> <div/>
```
inline-block 속성은 이렇게 두 태그 사이에 공백 넣으면 "공백크기만큼" 공간을 차지하기 때문에
공간이 부족해지면 다음 라인으로 밀려나감
- font-size 0px로 해결
- \<!---->(주석처리 문자)넣고 중간에 개행해서 해결
-> 둘다 좀 별로임

### 한문장으로 정리 : inline-block은 자기 크기만큼 자리를 차지하며, 공백 제거가 필요하고, 주변에 글이 있으면 이상해질 수 있다.

> float로 떠있는 요소 아래에 배치된 요소의 경우, margin-top을 먹여도 제대로 안먹음   
> 위의 요소가 떠있고, margin을 적용해도 떠있는 요소 아래의 빈공간에 마진이 들어가기 때문   
> clear:both를 쓰고 마진을 빈공간만큼 더 늘려서 적용해도 되지만, 두 요소 사이에 의미없는 div를 배치하는 것도 하나의 방법
---



```css
.navbar li{ ~ }

.navbar>li{ ~ }
```
## selector 문법 중 공백 또는 >
### a 안에 있는 b (위의 경우 navbar 안에 있는 li를 셀렉)
- \> : 직계 자식에만 적용
- 공백 : 모든 자식(저 li 안에 다른 li가 있을 경우, 해당 li도 셀렉)
## input[type=text]{...}
- 셀렉터 문법
- input 요소들에 다 적용하는 것이 아니라, type이 text인 것에만 적용
***


## 이미지를 배경으로 설정하고 각종 속성을 설정하는 css 코드
```css
    background-image: url(../img/shoes.jpg);
    background-size: cover; 
    /*100% || cover || contain; cover : 빈영역 꽉채워 contain : 이미지 안짤리게*/
    background-repeat: no-repeat;
    /*배경 크기에 따라 이미지가 반복되는 현상 제거*/
    background-position: center;
    /* background-attachment: scroll; */
    filter: sepia();
    /* 필터는 글자에도 필터 입혀짐 */
```
---

## margin-collapse 현상
### 두 박스의 위쪽 테두리가 겹칠 경우 margin이 합쳐짐
- 둘 중 하나에만 margin을 줘도 둘 모두에게 margin이 생김
- 상위 태그에 padding 을 넣는 등 둘의 테두리가 겹치지 않게 조정
> 윗 박스의 아래 테두리, 아랫 박스의 윗 테두리가 겹쳐도 마찬가지로 비슷한 문제가 발생함
- 윗 박스의 bottom 마진 50px, 아래 박스의 top 마진 30px을 설정할 경우, 80px만큼 간격이 생길 것 같지만 실제로는 둘 중 더 큰 50px의 마진만 생김


## position
### position 속성을 통해 요소들의 위치를 설정가능
```css
    position: absolute;
    /* position : 이동 가능, float 쓴 것 처럼 공중에 뜸*/
    /* static : 기준이 따로 없음. 이동 불가 */
    /* relative : 내 원래 위치를 기준으로 이동 */
    /* fixed : 화면의 특정 위치에 고정 */
    /* absolute : 내 부모태그 중  position : relative를 가진 부모를 기준으로 포지셔닝 */
```
### 특정 요소를 가운데 정렬하기 위해선 아래처럼 최소 5가지의 속성 부여
```css
.button {
  position : absolute; 
  left : 0;
  right : 0; 
  margin: auto;
  width : 적절히
}
/* 가운데 정렬을 위한 속성들 */
```
### position / float 등의 속성으로 '떠 있는 요소'들이 많다면?
> 어떤 요소가 앞으로 나올지에 대한 속성을 부여할 필요성 존재
- z-index
    - z-index가 높을수록 상위 레이어(앞으로 나옴)

### 박스의 크기를 #%로 하고싶지만, 화면이 크면(pc화면 등) 박스가 너무 커진다 !
- max-width 속성으로 최대 크기 설정가능
- 비슷한 맥락에서, min-width도 설정 가능

### max-width를 설정했는데, padding을 줬더니 최대 크기를 넘어간다 !
- max-width는 박스 전체가 아니라, 내부의 content의 영역의 최대값을 의미
- 박스 사이즈를 정확히 하고싶다면 content 부분만 width로 설정하지말고, padding, border 포함해서 크기 설정하기 가능
    - box-sizing: border-box; 이걸 활용하면 '실제로 보이는 영역'의 크기로 조정가능
    - box-sizing: content-box; 이게 default라고 생각
> css 파일에 div { box-sizing: border-box;} 해놓으면 편하겠다.   
> 비슷한 맥락에서 body{margin : 0px} 도 해놓으면 좋을듯


### 브라우저 호환성 : 브라우저마다 디자인이 다르게 보일 수 있다
- 호환성 이슈해결책부터 첨부하는 경우가 있다
- 검색 keywords : normalize.css
- https://github.com/necolas/normalize.css/blob/master/normalize.css

---

## Input & Form
### 사용자로부터 입력을 받아올 수 있다
```html
   <form action="">
        <input type="text" value="username">
        <!-- default값을 넣어두기 위해 value사용 -->
        <input type="password" placeholder="password">
        <!-- placeholder로 해당 영역의 용도 표시 가능 -->
        <input type="email" name="email">
        <!-- 서버로 보낼 때 'email:입력값' 형태로 전송하기위해 name 사용가능 -->
        <input type="date">
        <!-- 날짜입력 -->
        <input type="checkbox">
        <select>
            <option>aaa</option>
            <option>bbb</option>
            <option>ccc</option>
        </select>
        <!-- select-option으로 선택지 선택 가능 -->
        <textarea cols="30" rows="10"></textarea>
        <!-- 긴 텍스트를 입력받기 위한 textarea -->
        <!-- rows, cols는 default로 보여줄 크기 -->
        <input type="submit">
        <button type="submit">전송</button>
        <!-- input을 제출/전송하기 위한 버튼 -->

	<input type="checkbox" id="subscribe">
	<label for="subscribe">여길 눌러도 체크가 됩니다.</label>
    </form>
```
- 재사용 가능한 클래스 만들어 활용하는 것이 편함

- 셀렉터에 콤마(,) 써서 여러개 나열하면 동시에 속성 지정 가능

- <label> 태그를 활용해보자 - ex) checkbox에 label로 붙이면 해당 라벨을 눌러도 체크가 됨
