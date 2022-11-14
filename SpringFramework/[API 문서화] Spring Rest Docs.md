# **API 문서 = 설명서**

API 계층에서 Controller는 클라이언트에서 보낸 HTTP request URI를 통해 클라이언트의 요청을 전달받고, 서비스 계층, 데이터 액세스 계층을 거쳐 요청에 대한 처리를 실행한 후, 다시 클라이언트에 응답을 돌려준다.

이때, 클라이언트가 REST API 백엔드 앱에 요청을 전송하기 위해 알아야 할 요청 정보를 문서로 정리하는 것을 API문서화 라고 한다.

> **예를 들어 클라이언트에서 POST요청을 통해 새로운 회원을 등록하는 상황을 가정해보자**

-   회원을 등록하기 위해서는 필수로 입력해야 할 정보, Optional로 입력할 정보 등 여러 정보가 필요하다.
-   만약 이러한 정보들에 대해 알 수 없다면, 프론트 엔드에서는 정확한 요청을 보내는 것이 불가능하다.
-   따라서 요청에 필요한 정보들을 정리할 필요가 있다.

> **API 사용을 위한 어떠한 정보가 담긴 문서를 API 문서, 또는 API 스펙이라고 한다.**

# **API 문서 생성 자동화**

> **Spring Rest Docs, Swagger 등을 이용해 API 문서 생성을 자동화할 수 있다.**

-   자동화의 필요성
    -   수기로 직접 하나하나 작성하는 것은 생산성이 떨어진다.
    -   또한 직접 API 문서를 작성할 경우, 작성 도중 실수할 가능성이 존재한다.
    -   기능이 추가될 경우, 실제 API 정보와 수기로 작성한 API 문서의 정보가 다를 수 있다.

# **Spring Rest Docs**

> Swagger의 경우, Postman 처럼 API 요청 툴로써의 기능을 사용할 수 있다는 장점이 있지만,  
> 실제 애플리케이션 코드에 많은 양의 애너테이션이 추가되어야 하므로, 코드의 가독성이 떨어지고 유지보수가 어려워진다는 단점이 존재한다.

-   Spring Rest Docs는 애플리케이션의 기능 구현과 관련된 코드에는 아무런 정보도 추가되지 않는다.
    -   따라서 Swagger와 비교했을 때, 구현에 관련된 코드가 훠어어얼씬 깔끔하다.
-   대신, Slice test를 실행하는 테스트 클래스에 API 문서를 위한 정보를 추가해야 한다.
    -   만약 테스트가 실패한다면 API 문서(정확히는 각각의 Snippet) 또한 생성되지 않는다.
    -   테스트의 실행 결과가 passed 이면 테스트 코드에 포함된 API 스펙 정보 코드를 기반으로 API 문서 스니핏이 .adoc 확장자를 가진 파일로 생성된다.

# **API 문서 생성 흐름**

1.  테스트 코드 작성
    1.  슬라이스 테스트 코드 작성
    2.  API 스펙 정보 코드 작성(Request Body, Response body, Query Parameter 등의 정보)
2.  test 태스크 실행
    1.  성공 시, snippet 일괄 생성
    2.  실패 시, 테스트 케이스 수정 후 다시 테스트
3.  API 문서 스니핏 생성됨
4.  스니핏을 모아 하나의 API 문서 작성
5.  API 문서를 HTML로 변환

# **build.gradle 설정**

```java
//.adoc 파일 확장자를 가지는 AsciiDoc 문서를 생성해주는 Asciidoctor
//Asciidoctor 를 사용하기 위한 플러그인 추가
plugins {
	id 'org.springframework.boot' version '2.7.1'
	id 'io.spring.dependency-management' version '1.0.11.RELEASE'
	id "org.asciidoctor.jvm.convert" version "3.3.2"    // (1)
	id 'java'
}

repositories {
	mavenCentral()
}
//ext 변수의 set() 메서드를 이용해서 API 문서 스니핏이 생성될 경로를 지정
ext {
	set('snippetsDir', file("build/generated-snippets"))
}

//AsciiDoctor에서 사용되는 의존 그룹 지정
// :asciidoctor task가 실행되면 내부적으로 아래에 입력된 그룹을 지정
configurations {
	asciidoctorExtensions
}

dependencies {
    //아래를 통해 spring-restdocs-core와 spring-restdocs-mockmvc 의존 라이브러리가 추가된다.
	testImplementation 'org.springframework.restdocs:spring-restdocs-mockmvc'
  
	//spring-restdocs-asciidoctor 의존 라이브러리 추가
	asciidoctorExtensions 'org.springframework.restdocs:spring-restdocs-asciidoctor'

	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-validation'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	compileOnly 'org.projectlombok:lombok'
	runtimeOnly 'com.h2database:h2'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	implementation 'org.mapstruct:mapstruct:1.5.1.Final'
	annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.1.Final'
	implementation 'org.springframework.boot:spring-boot-starter-mail'

	implementation 'com.google.code.gson:gson'
}

// :test task 실행 시, 스니핏이 생성될 디렉토리 경로를 설정
tasks.named('test') {
	outputs.dir snippetsDir
	useJUnitPlatform()
}

// :asciidoctor task 실행 시, Asciidoctor 기능을 사용하기 위해 
// :asciidoctor task에 asciidoctorExtensions 설정
tasks.named('asciidoctor') {
	configurations "asciidoctorExtensions"
	inputs.dir snippetsDir
	dependsOn test
}

//:build task 실행 전에 실행되는 task
//:copyDocument task가 수행되면 index.html 파일이 src/main/resources/static/docs 에 복사됨
// 이렇게 복사된 index.html 파일은 API 문서를 파일 형태로 외부에 제공하기 위해 사용 가능
task copyDocument(type: Copy) {
	dependsOn asciidoctor  // :asciidoctor task가 실행된 후 task가 실행되도록 함
	from file("${asciidoctor.outputDir}")  // 복사할 대상 디렉토리
	into file("src/main/resources/static/docs")  //복사 결과를 추가할 디렉토리
}

build {
	dependsOn copyDocument  //copyDocument가 실행된 후 build가 실행되도록 설정
}

bootJar {
	dependsOn copyDocument 
	from ("${asciidoctor.outputDir}") {
		into 'static/docs'
	}
}
```
