# 캡슐화(Encapsulation)
### 캡슐화 : 연관된 속성(필드)와 기능(메서드)들을 묶어 하나의 캡슐로 만들고 내부에 감춰, 외부로부터 데이터를 보호하는 것
캡슐화의 목적은 크게 두 가지로, 데이터를 보호하는 목적과 내부적으로만 사용되는 데이터에 대한 외부 노출 방지 목적이 있다.   
요약하면 결국 '캡슐화는 정보은닉을 위한 것'인데, 캡슐화를 수행하기 위한 수단으로 접근제어자(Access Modifier)와 getter/setter 메서드가 있다.
***
### 접근제어자 : java의 접근제어자는 public, protected, default(package-private), private 로, 각각 접근 권한을 다르게 설정한다.   
각 접근제어자를 설명하기에 앞서 패키지(Package)에 대한 개념이 필요한데, 패키지는 특정한 목적을 공유하는 인터페이스와 클래스의 묶음을 지칭한다.   
패키지는 클래스들을 그룹 단위로 묶어 효과적으로 관리한다는 목적을 가지고있다.   
java에서의 패키지는 하나의 물리적인 디렉토리이며 Import 패키지명.* 을 통해 다른 패키지를 불러오고, 해당 패키지의 클래스를 사용할 수 있다.   

|---|클래스 내|패키지 내|다른 패키지의 <br> 하위클래스|패키지 외|
|:---:|:---:|:---:|:---:|:---:|
|public|O|O|O|O|
|protected|O|O|O|<span style="color:red">X</span>|
|default|O|O|<span style="color:red">X</span>|<span style="color:red">X</span>|
|private|O|<span style="color:red">X</span>|<span style="color:red">X</span>|<span style="color:red">X</span>|
