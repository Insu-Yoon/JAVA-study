# **보안이 적용된 웹 요청의 일반적인 처리 흐름**

![image{"caption":"인증 처리 흐름 Flowchart"}](https://user-images.githubusercontent.com/110891599/203330372-f8aa0d08-8cbb-439d-9c04-d8cf9c8c9f1f.png)

1.  사용자의 리소스에 대한 접근 요청
2.  인증 관리자로부터의 크리덴셜 요청
3.  사용자가 인증 관리자에게 크리덴셜 제공
4.  인증 관리자가 크리덴셜 저장소에서 사용자의 크리덴셜 조회
5.  인증 관리자는 사용자가 제공한 크리덴셜과 크리덴셜 저장소에서 조회한 크리덴셜을 비교하여 검증 수행  
    -   유효한 크리덴셜이 아니라면 Exception throw
6.  접근 결정 관리자는 사용자가 적절한 권한을 부여받았는지 검증
    -   요청한 리소스에 접근할 수 있는 권한이 없다면 Exception throw
7.  적절한 권한을 가졌다면 리소스의 접근 허용

# **Spring Security에서의 Component + 인증 처리 흐름**

![image](https://user-images.githubusercontent.com/110891599/203330630-9b3733d4-3767-4b88-9d2d-28e69995bd15.png)
