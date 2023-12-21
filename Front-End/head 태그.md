# head 에 포함될 수 있는 것들
## vscode 기본 제공 head
```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Document</title>
</head>
<body>
  
</body>
</html>
<!-- vscode에서 ! tab을 누르면 자동으로 완성되는 기본 폼 -->
```
- html lang: 자동 번역 기능이나, 스크린 리더와 관계된 옵션임 (웹 접근성과 관련)
- meta charset : 인코딩 형식 지정
- viewport : 환경에 따라 화면의 해상도가 다를 수 있는데, width를 device-width와 일치시키고 스케일링해 렌더링함. (반응형 웹과 관련)
- title : 브라우저의 제목표시줄에 표시될 이름

## 추가로 설정할 수 있는 요소들
```html
  <link href="css/main.css" rel="stylesheet">
  <link rel="icon" href="아이콘경로.ico" type="image/icon">
  <meta property="og:image" content="/이미지경로.jpg">
  <meta property="og:description" content="사이트설명">
  <meta property="og:title" content="사이트제목">
```
- link href="" rel="stylesheet" : css 파일을 지정
- link href="" rel="icon" : 제목 표시줄에서 title 앞에 표시될 아이콘(favicon) 지정
- og meta-tag : sns나 디스코드 등에서 url 입력 시 링크에 이미지, 타이틀, 간단한 설명등이 임베디드 되는 경우가 있는데, 해당 임베디드를 커스터마이징 하고싶을때 사용
