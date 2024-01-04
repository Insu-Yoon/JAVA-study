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


문자열.includes('검사할문자열') -> out : boolean

정규식
/a/.test('abcde') = 'abcde' 안에 a가 있는가? (true)
/[a-z]/  범위
^a : a로 시작
a$ : a로 끝
a|b : a or b

스크롤 이벤트 리스너
window.addEventListener('scroll', function);
scrollY() - 현재 스크롤한 위치
scrollTo(x,y) - 스크롤 할 위치
scrollBy(x,y) - 한번에 이만큼 스크롤

scrollTop = div에 적용가능한 scrollY (스크롤 내린 양)
clientHeight : 눈에 보이는 div 높이
scrollHeight : 스크롤 가능한 실제 높이

$('.asdf').eq(0) : 셀렉된애들중 첫번째(jq 형태의 return) -> 뒤에 jquery 함수 호출할때 사용
$('.asdf').slice(0,4) : 셀렉된 애들중 0~3번 인덱스만

$('.class')[0] -> jquery로 뽑은걸 document.querySelector('.class')로 뽑은 형태로 바꿀때
$('.class').eq(0)
$(event.target).is($('.class'))

document.querySelector('.black-bg')
결과 <div class=​"black-bg">​…​</div>​
$('.black-bg')
결과 jQuery.fn.init {0: div.black-bg, length: 1, prevObject: j…y.fn.init}
$('.black-bg')[0]
결과 <div class=​"black-bg">​…​</div>​
$('.black-bg').eq(0)
결과 jQuery.fn.init {0: div.black-bg, length: 1, prevObject: j…y.fn.init}



js 라이브러리
- swiper : 캐러셀(이미지 슬라이드) 라이브러리
  - https://swiperjs.com/get-started#use-swiper-from-cdn 여기 튜토리얼대로
- chart.js : 차트 그려줌
  - https://cdnjs.com/libraries/Chart.js 이런데서 js 받거나 cdn 버전으로 구해서 html에 삽입. https://www.chartjs.org/docs/latest/ 홈페이지 예제코드 아무데나 붙여넣어도 나옴
- AOS(Animation on scroll) : 스크롤 내리면 element가 서서히 등장하거나 하는 애니메이션 넣기 가능
  - https://github.com/michalsnik/aos 에서 css, js cdn 찾아서 html에 넣고 script에 AOS.init(); 넣으면 끝
- EmailJS : 서버 빌려서 js만으로 메일 전송 가능(내 이메일로 다른데 보내거나, 다른 이메일로 내 이메일에 보내기 ㄱㄴ)
  - https://www.emailjs.com/docs/introduction/how-does-emailjs-work/ 여기가서 가입, 로그인, 튜토리얼
- Lodash : array, object, 문자, 숫자 자료를 다루기 편해지는 기본함수들을 제공
  - https://lodash.com/
- Fullpage.js : 웹 페이지를 ppt 처럼 만들어줌
  - https://github.com/alvarotrigo/fullPage.js/tree/master/lang/korean#fullpagejs 여기서 css, js 받거나 cdn식으로 받으면되고 예시 html, css 참고


eventListener로 동작할 때 function 내부에서 this 는 event.target이랑 같음
근데 function말고 arrow function( ()=> ) 으로 쓰면 외부의 this값을 가져옴
용도에 맞게사용

ajax : 새로고침 없이 GET POST 가능
$.ajaxSetup({ async: false }); 를 선언해두면 ajax관련 요청을 보낼때 synchronous방식으로 사용가능. 요청이 완료된 후 나머지 코드 실행
