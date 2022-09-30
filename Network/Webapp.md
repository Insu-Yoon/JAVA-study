# **웹 애플리케이션**

애플리케이션을 큰 분류로 둘로 나누면 웹 애플리케이션(Web Application)과 네이티브 애플리케이션(Native Application)으로 나눌 수 있다. 둘을 간단히 비교하면 다음과 같다.

|   | **웹 앱** | **네이티브 앱** |
| --- | --- | --- |
| **장점** | 브라우저에서 실행되므로 설치, 다운이 필요하지 않다. <br>  업데이트 등 유지보수가 비교적 쉽다. <br>  앱 스토어의 승인 절차가 필요하지 않다.   <br>실행 환경에 대한 종속성이 없다. | 인터넷 없이도 실행이 가능하다.   <br>웹 앱에 비해 속도가 빠르다.   <br>앱이 설치된 시스템/기기의 리소스에 접근하기 쉽다.  <br> 앱 스토어의 승인이 필요하므로 비교적 안전하다. |
| **단점** | 인터넷이 없으면 아예 사용할 수 없다. <br>  네이티브 앱에 비해 속도가 느리다. <br>  네이티브 앱에 비해 접근성이 떨어진다. <br>  보안상 위험에 노출되기 쉽다. | 크로스 플랫폼을 고려해야 하므로, 개발비가 더 든다.   <br>빠른 업데이트가 힘들다. (버전 수정, 앱 승인, 배포) <br>  앱 스토어 승인 절차가 까다롭고 비용이 발생한다. |

## **웹 앱 3-tier 아키텍쳐**

![image](https://user-images.githubusercontent.com/110891599/193285503-052b0586-7457-46f9-a563-bfe09893c211.png)


-   Presentation Tier(Client) :  브라우저 등을 통해 직접적으로 유저와 접촉하는 계층이다. 웹 서버가 이 계층에 속하며 유저 인터페이스 요소들을 포함한다.
-   Logic Tier(Application) : Business Logic이 실행되는 계층이다. 유저의 요청을 브라우저로부터 받아 처리한다. 앱 서버가 이 계층에 속하며, 데이터 접근을 위한 경로의 규격화 등의 과정이 이 계층에 작성된다.
-   Data Tier(Database) : 앱의 데이터 저장소에 접근하여 데이터를 불러오거나, 저장한다. 

## **SSR vs CSR**

각각 Server Side Rendering, Client Side Rendering의 약자이다.

### **Server Side Rendering**

단어 의미만 설명하면 '웹페이지를 서버에서 렌더링하여 브라우저로 전송한다.'는 의미이다.

실제로 브라우저에서 서버의 URI로 GET요청을 보내면, 서버는 정해진 웹 페이지 파일을 브라우저로 보내고, 브라우저에 웹 페이지 파일이 도착하면 완전히 렌더링된다. 사용자가 다른 경로로 이동하거나 새로고침 등을 수행하면 매번 위의 과정이 실행된다.

-   SSR의 장점
    -   Search Engine Optimization의 우선순위가 높을 경우 CSR보다 SSR로 구현하는 것이 유리하다.  
        (내용이 들어있는 페이지이기 때문에, 검색에 노출된다.)
    -   웹 페이지의 첫 화면 렌더링이 빠르다.
-   SSR의 단점
    -   서버측에서 렌더링을 부담하기 때문에 앱 유지비용이 높다.
    -   일부 서드파티 자바스크립트 라이브러리는 SSR이 불가능할 수 있다.
    -   유저와 웹 앱의 상호작용이 빈번할 경우 비효율적이다.

### **Client Side Rendering**

SSR과는 반대로, 클라이언트 측에서 렌더링을 수행한다. 서버는 웹페이지의 골격이 될 페이지와 JavaScript 파일만 브라우저로 보내고, 클라이언트가 이를 받아 JavaScript를 실행하여 웹 페이지를 완전히 렌더링 된 페이지로 바꾼다. CSR의 경우 SSR과 다르게 서버가 웹 페이지를 다시 보내지 않는다.

-   CSR의 장점
    -   유저와 웹 앱의 상호작용이 빈번할 경우, 빠른 라우팅으로 훌륭한 사용자 경험을 제공한다.
-   CSR의 단점
    -   유저의 사양에 따라 느린 렌더링으로 인해 부정적인 사용자 경험을 제공할 수 있다.
    -   Search Engine bots와 상성이 좋지않다. 일반적인 경우, 렌더링 될 정보들은 Search Engine index에 포함되지 않을 것이다. (사실 현재는 next.js를 통해 이러한 단점을 상쇄할 수 있다.)

Reference

그림 1. [https://docs.aws.amazon.com/whitepapers/latest/serverless-multi-tier-architectures-api-gateway-lambda/three-tier-architecture-overview.html](https://docs.aws.amazon.com/whitepapers/latest/serverless-multi-tier-architectures-api-gateway-lambda/three-tier-architecture-overview.html)
