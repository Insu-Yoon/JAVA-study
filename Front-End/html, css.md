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
```html
#special { font-size : 30px } /*아이디*/
.profile { font-size : 20px }  /*클래스*/
p { font-size : 16px } /*태그*/
```
- css 파일에서 id, 클래스, 태그에 대한 스타일 지정 가능
- 스타일에서 서로 충돌이 발생할 시 우선순위에 따라 적용
- 위 코드에서 위쪽이 더 우선순위가 높음
- 가장 우선 순위가 높은 건 html에서 직접 지정한 스타일
