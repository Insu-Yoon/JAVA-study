# SCSS
- CSS에서 필연적으로 발생하는 가독성 문제, 유지보수 문제등을 보완하기 위해 나온 개념
- 코드의 재활용성을 올리고, 가독성을 올리는 등 CSS의 단점을 보완하고 개발 효율을 높이는 것이 목적

## SCSS to CSS
브라우저는 HTML, CSS, JavaScript 밖에 못읽는다. 따라서 scss로 작성해도, css로 컴파일해줘야 브라우저가 이해함

## SCSS 활용 예시
### 1. 변수 개념
```scss
$main-color = #2a4cb2;

.custom{
  background-color:$main-color;
}
```
위 코드처럼 특정 변수에 값을 할당해두고 편하게 쓸 수 있음   
또는 아래처럼 활용도 가능
```scss
$my-font-size = 20px;
custom-h4{
  font-size: $my-font-size + 2px;
}
custom-p{
  font-size: $my-font-size - 2px;
}
```
계산도 먹히기 때문에, 이렇게 사칙연산 적용해서 쓸수도 있음   
단, 사칙연산을 적용할 경우 단위는 같아야하고, 곱하기나 나누기의 경우 단위가 이상해질 수 있으니 유의

### 2. Nesting 문법
셀렉터로 이어서 적고 스타일링 하던걸, 메서드 안에 메서드 넣고 작성하듯 작성가능
```scss
.main-bg h4{
  font-size: 16px;
}
.main-bg button{
  color: red;
}
/* 위 코드를 아래처럼 작성 가능*/
.main-bg{
  h4{
    font-size: 16px
  }
  button{
    color: red;
  }
}
```
`main-bg` 라는 클래스에 관련된 애들 한번에 처리할 수 있으므로   
서로 관련있는 클래스들을 묶어줄 때 편함. 하지만 2중첩 3중첩하면 보기에도 지저분하고 오히려 관리하기 힘들어서 그냥 1중첩정도만 하는게 일반적

**nesting 문법 쓸 때, pseudo class(:hover) 같은거 쓰려면 아래처럼 & 붙여서 써야됨**
```scss
.navbar{
  &:hover{ 스타일링 }
}
```
이렇게 해야 `.navbar:hover{...}`로 적용됨. & 안붙이면 `.navbar :hover{}` 이렇게 스페이스바 하나 들어간걸로 적용됨

### 3. @extend 문법
중복적인 스타일을 하나로 따로 빼두고, `@extend` 활용해서 코드 재활용 가능
```scss
%btn{ 기본 스타일 }
.btn-green{
  @extend %btn;
  background-color : green;
}
```

### 4. @mixin, @include 문법
`@extend`랑 비슷한 개념이긴 한데, 이건 변수를 할당해서 활용가능
```scss
@mixin font-style($size, $space){
  font-size:$size;
  letter-space:$space;
}

.custom-p{
  @include font-style(20px, -1px);
}
```

### 5. 다른 파일에 있는 내용을 가져오고 싶다면?
```scss
/*다른 scss 파일( reset.scss )*/
body{
  margin:0;
} 
div{
  box-sizing:border-box;
}

/* 스타일링 할 scss 파일 */
@use 'reset';
```
다른 scss 파일에서 설정한 특정 스타일링을 가져오고 싶으면 위처럼 `@use '파일명'` 으로 사용가능   
해당 파일에서 `$main-color:blue;` 이런식으로 변수에 값을 넣어뒀다면, 그것도 끌어다가 쓸 수 있음   
끌어오는 방법은 `reset.$main-color` 이렇게.
