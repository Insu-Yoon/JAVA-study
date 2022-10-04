> **필요한 기반 지식) 클라이언트-서버 아키텍처, HTTP 통신**

# **REST API**

> **REST API = Representational State Transfer + API**

웹 앱에서는 HTTP 메서드를 통해 서버와 통신한다. HTTP의 메서드에는 POST, GET, PUT, PATCH, DELETE 등이 있으며, 당연히 클라이언트와 서버가 통신을 할 때 어떤 요청을 주고받느냐에 따라 메서드의 사용이 달라질 것이다.

이렇게 메서드를 사용할 때 '올바른 방법'이 존재하는데, 이 올바른 방법을 정의하여 일종의 규약으로 만든 것이 REST API 이다.

REST API는 웹에서 사용되는 데이터나 자원을 HTTP URI로 표현하고, HTTP 프로토콜을 통해 요청과 응답을 정의하는 방식을 의미한다. 이 요청과 응답을 '잘' 하기 위해서는 서로가 알아보기 쉽도록 작성하는 것이 중요하다.

그렇다면 어떻게 작성해야 알아보기 쉬울지, REST API 성숙도 모델을 통해 알아보자.

# **REST API 성숙도 모델**

REST API 성숙도 모델은 REST API를 더욱 잘 적용하기 위해 고안된 모델로 0~3 단계, 총 4단계로 이루어진다. REST API를 처음으로 소개한 로이 필딩은 모든 단계를 충족해야 REST API라고 부를 수 있다고 주장했지만, 실제로는 엄밀하게 3단계까지 모두 지키기 어렵기 때문에 2단계 까지만 적용해도 좋은 API디자인이라고 볼 수 있으며 이를 HTTP API라고도 부른다.

## **REST API - 0단계**

REST API 성숙도 모델에 따르면, 0단계 에서는 단순히 HTTP 프로토콜을 사용하기만 해도 0단계를 충족한다. 이 경우 해당 API를 REST API라고는 할 수 없지만, 좋은 REST API를 작성하기 위한 기본이라고 할 수 있다.

| 요청 내용 | 요청 | 응답 |
| --- | --- | --- |
| 예약 가능   방 확인 | POST /booking HTTP/1.1   \[헤더 생략\]      {     "date" : "2022-10-08",     "room" : "Heaven"   } | HTTP/1.1 200 OK   \[헤더 생략\]      {     "slots" : \[       { "room" : "Heaven", "start" : "08:30", "end" : "18:30" },       { "room" : "Heaven", "start" : "19:30", "end" : "07:30" }     \]   } |
| 특정 일자   예약 | POST /booking HTTP/1.1   \[헤더 생략\]      {     "room" : "Heaven",     "start" : "08:30",     "end" : "18:30",     "booker" : "ingsu"   } | HTTP/1.1 200 OK   \[헤더 생략\] |

### **REST API - 1단계**

1단계에서는 개별 리소스와의 통신을 준수해야 한다.

-   모든 자원은 개별 리소스에 맞는 엔드 포인트(Endpoint)를 사용해야 한다.
-   요청하고 받은 자원에 대한 정보를 응답으로 전달해야 한다.

0단계에서는 모든 요청에서 엔드포인트로 /booking 을 사용했다. 하지만 1단계에서는 요청하는 리소스가 무엇이냐에 따라 다른 엔드포인트로 구분하여 사용해야 한다.

| 요청 내용 | 요청 | 응답 |
| --- | --- | --- |
| 예약 가능   방 확인 | POST /rooms/Heaven HTTP/1.1   \[헤더 생략\]      {     "date" : "2022-10-08"   } | HTTP/1.1 200 OK   \[헤더 생략\]      {     "slots" : \[       { "id":"0", "room":"Heaven", "start":"08:30", "end":"18:30" },       { "id":"1", "room":"Heaven", "start":"19:30", "end":"07:30" }     \]   } |
| 특정 일자   예약 | POST /slots/0 HTTP/1.1   \[헤더 생략\]      {     "booker" : "ingsu"   } | HTTP/1.1 200 OK   \[헤더 생략\]      {     "booking" : {        "slot" : {"id" : "0", "room" : "Heaven", ... },        "booker" : "ingsu"     }   } |

위의 표에서 예약 가능한 방 확인이라는 요청을 통해 받게되는 응답의 리소스는 Heaven이라는 방의 예약 가능한 시간대이다. 그렇기 때문에 /rooms/Heaven이라는 엔드포인트를 사용한 것을 볼 수 있다.

특정 시간에 예약하게 되면 slot이라는 리소스의 0이라는 id를 가진 리소스가 변경되기 때문에, 하단의 특정 시간에 요청이라는 요청에서는 /slot/0 이라는 엔드포인트를 사용했다.

1단계에서는 응답으로 리소스를 전달할 때도 사용한 리소스에 대한 정보와 함께 리소스 사용에 대한 성공/실패 여부를 반환해야 한다. 위의 예시에서는 booking에 성공하였으나, 실패한 경우에는 아래와 같이 실패 여부를 포함한 응답을 반환해야 한다.

| 요청 내용 | 요청 | 응답 |
| --- | --- | --- |
| 예약 가능   방 확인 | POST /rooms/Heaven HTTP/1.1   \[헤더 생략\]      {     "date" : "2022-10-08"   } | HTTP/1.1 200 OK   \[헤더 생략\]      {     "slots" : \[       { "id":"0", "room":"Heaven", "start":"08:30", "end":"18:30" },       { "id":"1", "room":"Heaven", "start":"19:30", "end":"07:30" }     \]   } |
| 특정 일자   예약 | POST /slots/0 HTTP/1.1   \[헤더 생략\]      {     "booker" : "ingsu"   } | HTTP/1.1 409 Conflict   \[헤더 생략\]      {     "bookingFailure" : {        "slot" : {"id" : "0", "room" : "Heaven", ... },        "booker" : "ingsu"        "reason" : "해당 시간은 이미 예약되어있습니다."     }   } |

## **REST API 2단계**

> **CRUD : Create, Read, Update, Delete**

2단계에서는 CRUD에 맞게 적절한 HTTP 메서드를 사용하는 것에 중점을 둔다. 0, 1 단계의 예시를 보면 모두 POST 메서드를 통해 요청하고있다. 하지만 CRUD에 맞게 메서드를 사용한다면 "예약 가능 방 확인"은 GET으로 하는 것이 적절할 것이다. HTTP에서 GET 메서드는 바디를 포함할 수 없으므로, query parameter를 통해 필요한 리소스를 전달한다.

2단계에서는 요청에 대한 응답도 더 구체적으로 해야한다. POST 메서드에 대한 성공 응답은 201 Created로 명확하게 작성해야 할 것이다. 또한 관련 리소스를 클라이언트가 Location 헤더에 작성된 URI를 통해 확인할 수 있도록 해야 2단계를 충족했다고 할 수 있다.

| 요청 내용 | 요청 | 응답 |
| --- | --- | --- |
| 예약 가능   방 확인 | GET /rooms/Heaven/slots?date=2022-10-08 HTTP/1.1   \[헤더 생략\] | HTTP/1.1 200 OK   \[헤더 생략\]      {     "slots" : \[       { "id":"0", "room":"Heaven",  ...},       { "id":"1", "room":"Heaven",  ...}     \]   } |
| 특정 일자   예약 | POST /slots/0 HTTP/1.1   \[헤더 생략\]      {     "booker" : "ingsu"   } | HTTP/1.1 201 Created   Location: slot/0/booking   \[헤더 생략\]      {     "booking" : {        "slot" : {"id":"0", "room":"Heaven", ... },        "booker" : "ingsu"     }   } |

메서드를 사용할 때도 규칙이 있다.

-   GET 메서드를 사용할 때는 서버의 데이터를 변화시키지 않는 요청에 사용해야 한다.
-   POST 는 요청마다 새로운 리소스를 생성하며, PUT은 요청마다 같은 리소스를 반환한다.
    -   매 요청마다 같은 리소스를 반환하는 특징을 "멱등(idempotent)하다"고 한다.
    -   PUT은 멱등성을 가지며, POST는 멱등성을 가지지 않는다. 이를 잘 구분하여 사용해야 한다.
-   PUT과 PATCH도 구분하여 사용해야 한다.
    -   PUT은 전체를 교체하는 용도로 사용도니다.
    -   PATCH는 일부를 수정하는 용도로 사용된다.

## **REST API 3단계**

3단계에서는 HATEOAS(Hypertext As The Engine Of Application State)로 표현되는 하이퍼미디어 컨트롤을 적용한다.

3단계의 요청은 2단계와 동일하지만, 응답의 경우 리소스의 URI를 포함한 링크 요소를 삽입해 작성해야한다는 차이가 있다.

| 요청 내용 | 요청 | 응답 |
| --- | --- | --- |
| 예약 가능   방 확인 | GET /rooms/Heaven/slots?date=2022-10-08 HTTP/1.1   \[헤더 생략\] | HTTP/1.1 200 OK   \[헤더 생략\]      {     "slots" : \[       { "id":"0", "room":"Heaven",  ...},       { "id":"1", "room":"Heaven",  ...}     \],     "links" : {       "booking" : {         "href":"http://localhost:8080/slots/0",         "method":"Post"       }     }   } |
| 특정 일자   예약 | POST /slots/0 HTTP/1.1   \[헤더 생략\]      {     "booker" : "ingsu"   } | HTTP/1.1 201 Created   Location: slot/0/booking   \[헤더 생략\]      {     "booking" : {        "slot" : {"id":"0", "room":"Heaven", ... },        "booker" : "ingsu"     },     "links" : {        "self" : {          "href" : "http://localhost:8080/slots/0",          "method" : "GET"         },        "cancel" : {          "href" : "http://localhost:8080/slots/0/cancel",          "method" : "DELETE"         }      }     } |

예시와 같이, 예약 가능한 방을 확인한 경우 해당 시간대에 예약할 수 있도록 링크를 삽입하거나, 특정 시간에 예약을 하고나서는 해당 예약을 조회하거나 취소할 수 있도록 링크를 삽입할 수 있을 것이다.

이렇게 응답 내에 새로운 링크를 넣어 새로운 기능에 접근할 수 있도록 하는 것이 3단계의 중요 포인트이다.

# **여러 API 디자인 가이드**

-   [5가지의 기본적인 REST API 디자인 가이드](https://blog.restcase.com/5-basic-rest-api-design-guidelines/)
-   [호주 정부 API 작성 가이드](https://api.gov.au/standards/national_api_standards/)
-   [구글 API 작성 가이드](https://cloud.google.com/apis/design?hl=ko)
-   [MS의 REST API 가이드라인](https://github.com/Microsoft/api-guidelines/blob/master/Guidelines.md)
