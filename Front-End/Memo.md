js는 html에 연결되어있음
.js에서 document.title = new title 하면 바뀜

document.getElementById("id명") 으로 html에 접근가능

요약) js를 통해 document에서 항목들을 가져오고, 변경할 수 있음

쿼리셀렉터
document.querySelector(".class-name div")
css에서 스타일링할 요소를 셀렉터로 지정하는 것과 똑같이
css에서 사용하는 셀럭터를 쿼리로 입력해서 요소를 가져올 수 있음

이벤트
javascript는 웹 환경에서 발생할 수 있는 거의 모든 일을 listening할 수 있음
그 중에 우리가 원하는 event를 listen하도록 지정하고, 거기에 따른 동작을 function으로 지정 가능
title.addEventListener("click", handleTitleClick(함수명);
이러면 'title'에 대해 "click" 이벤트가 발생했을때 handleTitleClick에 지정된 동작을 함
title.onclick = handleTitleClick;
이렇게 해도 위랑 똑같음.
대신 위의 방식으로 하면 나중에 title.removeEventListener 사용가능

요약) js를 통해 document에서 항목을 가져오고, eventListener로 이벤트를 감지해서, 원하는 function을 실행할 수 있다.

h1.classList.toggle(토글할 클래스);

뭐가 나올지, 어떻게 값 꺼내야될지, 뭘 건드릴 수 있는지 보고싶으면
console.dir( 들여다볼 대상 )


<form> 태그 사용시 
- <input> 태그안에서 validation 가능
- 기본적으로 엔터 or 버튼 누르면 submit되고 페이지가 새로고침됨
- function f(event){ event.preventDefault() } 를 통해 브라우저의 기본동작(여기선 새로고침)을 막을 수 있음
- 브라우저 기본동작
  - form : submit
  - a : 다른 페이지로 이동

"hello "+username
= `hello ${username}`

localStorage 저장/가져오기 가능
(Session Storage, IndexedDb, Web SQL, Cookies, Trust Token등등 다른 방법도 많음)


setInterval(실행할 함수, 간격(ms))
특정 시간마다 함수를 실행할 수 있음

setTimeout(실행할 함수, 시간(ms))
특정 시간이 지난 후에 함수를 실행할 수 있음

Date 객체
new Date() 해서 쓸수있음

padStart(2,'0') = 문자앞에 뭐 추가하고싶을때(길이 지정) 문자가 2글자가 안되면 그만큼 앞에 "0" 채워넣어줘
뒤에 넣고싶으면 padEnd()

number parse to String
String(number) or number.toString()

Math.random() : 0~1사이 난수
latitude: 36.7964468, longitude: 127.1580071
js로 html 에 배경삽입
const backgroundImage = document.createElement("img");

eventListener가 click을 감지하도록 했다면, event.target으로 어디가 클릭됐는지 알 수 있음

버튼으로 삭제할 때, localStorage에서도 삭제하고싶음
근데 텍스트상으로는 중복이 발생할 수 있음
-> 중복을 막기위해 id를 함께 저장
-> 지울 때 id로 필터링해서 삭제

js는 배열에서 특정 요소 삭제하려면 해당 요소가 없는 새로운 배열을 만듦
배열.filter(필터링정보담긴function)
function의 결과 true를 리턴하면 새로운 배열에 포함, false면 제외

geolocation.getCurrentPosition()은 조회 성공시 콜백함수에 geoLocationPosition 을 넘겨줌
