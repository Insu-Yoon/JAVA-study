# **OAuth2 (Open Authorization)**

사용자 정보를 보유하고있는 서드파티 앱에서 사용자 인증을 대신 처리  
\= Resource에 대한 자격 증명용 토큰을 발급한 후  
Client가 해당 토큰을 이용해 서드파티 앱의 서비스를 이용  
(다른 App 사용에 필요한 인증 정보를 앱에 직접 저장하지 않고 사용)

# **OAuth2 구성 요소**

Resource Owner  
Client  
Resource Server   
Authorization Server

# **동작 방식**

1. OAuth 2 인증 요청  
2. Google 로그인 페이지로 Redirect  
3. 로그인 인증  
4. 엑세스 토큰 전송  
5. 리소스 오너의 리소스 요청  
6. 리소스 전송
