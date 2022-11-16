# **빌드**

-   IntelliJ 환경 : 화면 우측 상단의 Gradle - bootjar 또는 build 실행
    -   bootjar : Jar 파일을 생성하기 위한 task만 실행
    -   build : 빌드와 관련된 모든 task 실행

![image](https://user-images.githubusercontent.com/110891599/202191626-6816cf8a-c62e-4670-ad00-219c82971655.png)


-   Windows 터미널을 사용하여 빌드

```
//파일이 있는 디렉토리에서
.\gradlew bootJar
```

-   Git Bash를 사용하여 빌드

```
//파일이 있는 디렉토리에서
./gradlew build
```

# **실행**

-   빌드를 통해 생성된 Jar 파일이 존재하는 디렉토리로 이동
-   터미널을 통해 실행

```
//터미널에 입력
java -jar 파일명.jar
```

# **배포**

-   scp, sftp 등의 표준 유닉스 툴을 이용하여 Executable Jar 파일을 서버로 전송
-   PaaS(Platform as a Service) 서비스를 통해 클라우드 환경에 배포
    -   Cloud Foundry, Heroku 등
-   IaaS(Infrastructure as a Service)
    -   아마존의 AWS Elastic Beanstalk, AWS Container Registry, AWS Code Deploy 등
    -   Microsoft의  Azure도 Azure Spring Cloud, Azure App Service 에서 Executable Jar 배포 기능을 제공
    -   Google의 Google Cloud도 동일
-   CI / CD 플랫폼을 사용한 배포
    -   Github Actions나 Circle CI 등의 플랫폼을 이용해 AWS, Azure 등의 클라우드 서비스에 파일을 자동 배포하도록 구성 가능
