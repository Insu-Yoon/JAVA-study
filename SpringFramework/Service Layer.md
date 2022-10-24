> **흐름 잡기**  
> **\[Spring\] DTO 포스트에서는 '클라이언트의 요청을 API 계층으로 받아오는 과정'을 다뤘다면**  
> **이번 포스트에서는 'API 계층으로 받아온 데이터를 서비스 계층에서 사용하기 위한 방법' 과 그 과정에서 필요한 '매핑' 에 대해 정리해볼 것이다.**  
> **\- 기반 지식 : Dependecy Injection**

# **API 계층과 서비스 계층의 연동(feat.DI)**

> **API 계층과 서비스 계층을 연동한다는 말은, API계층에서 구현한 Controller 클래스가 서비스 계층의 Service 클래스와 메서드 호출을 통해 상호작용 한다는 것을 의미한다.**

![image](https://user-images.githubusercontent.com/110891599/197567503-f78ed592-7fb5-4caf-975c-c5256bacb618.png)


현재 패키지에는 클라이언트의 요청을 받아오는 과정에 필요한 DTO 클래스와, DTO 객체로 서비스 로직을 실행하는 Controller가 존재한다.

현재 목표는 'API 계층에서 받아온 데이터를 서비스 계층으로 넘기고, 서비스 로직을 처리한 후 다시 API 계층으로 응답을 넘겨주는 것이다.

-   우선 API 계층에서 DTO 를 사용했다면, 서비스 계층에서는 Entity를 사용한다.
    -   도메인 엔티티 클래스로 사용할 Member 클래스를 생성

```java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private long memberId;
    private String name;
    private String email;
    private String phone;
}
```

-   서비스 계층이므로, 서비스 클래스를 생성한다
    -   MemberService 클래스 생성

```java
@Service
public class MemberService {
    public Member createMember(Member member){
        return member;
    }

    public Member updateMember(Member member){
        return member;
    }

    public Member findMember(long memberId){
        Member member = new Member(memberId, "ingsu",
                "ingsu814@gmail.com","010-0101-2020");
        return member;
    }

    public List<Member> findMembers(){
        List<Member> members = List.of(
                new Member(1L,"홍길동", "hgd@gmail.com","010-0404-0505"),
                new Member(2L,"엄복동","ubd@gmail.com","010-8282-7788")
        );
        return members;
    }

    public void deleteMember(long memberId){
    }
}
```

createMember, updateMember의 경우 입력받은 DTO를 Member타입으로 변환 후, 해당 메서드에 입력하고 그대로 리턴한다.

findMember와 findMembers의 경우 Stub 데이터를 넣어두었다.

-   API계층에서 넘어온 DTO를 Entity로 매핑한다
    -   MemberMapper 인터페이스 생성

```java
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberPostDtoToMember(MemberPostDto memberPostDto);
    Member memberPatchDtoToMember(MemberPatchDto memberPatchDto);
    MemberResponseDto memberToMemberResponseDto(Member member);
}
```

Mapper 인터페이스는 mapstruct의 애너테이션을 사용하여 구현한다.

각각의 메서드는 DTO를 Member(Entity클래스) 타입으로 변환하거나, 그 반대의 동작을 한다.

-   서비스 로직 처리 후, 다시 DTO로 변환할 필요가 있다.
    -   MemberResponseDto 클래스 생성

```java
@Getter
@AllArgsConstructor
public class MemberResponseDto {
    private long memberId;
    private String name;
    private String email;
    private String phone;
}
```

> **이제 Controller 클래스에서 MemberService의 기능을 사용하도록 설정할 수 있다.**  
> **이때, DTO를 그대로 가져다가 쓰는 것이 아니라, MemberMapper를 통해 Entity 객체로 변환 후 사용할 것이다.**

MemberController 클래스의 모습은 아래와 같다.

```java
@RestController
@RequestMapping("/ing-study/members")
@Validated
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    public MemberController(MemberService memberService, MemberMapper memberMapper) {
        this.memberService = memberService;
        this.memberMapper = memberMapper;
    }

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberPostDto memberPostDto) {
        Member member = memberMapper.memberPostDtoToMember(memberPostDto);
        Member response = memberService.createMember(member);
        MemberResponseDto responseDto = memberMapper.memberToMemberResponseDto(response);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive long memberId,
                                      @Valid @RequestBody MemberPatchDto memberPatchDto) {
        memberPatchDto.setMemberId(1L);
        Member member = memberMapper.memberPatchDtoToMember(memberPatchDto);
        Member response = memberService.updateMember(member);
        MemberResponseDto responseDto = memberMapper.memberToMemberResponseDto(response);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId) {
        Member member = memberService.findMember(memberId);
        MemberResponseDto responseDto = memberMapper.memberToMemberResponseDto(member);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getMembers() {
        List<Member> members = memberService.findMembers();
        List<MemberResponseDto> responseDtos =
                members.stream()
                        .map(member -> memberMapper.memberToMemberResponseDto(member))
                        .collect(Collectors.toList());
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId) {
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
```

> **중요) 생성자를 통해 DI가 이루어진 모습을 확인할 수 있다.   
> 생성자를 통해 MemberController에 MemberService와 MemberMapper를 주입함으로써, 각 클래스의 메서드를 호출하여 사용할 수 있다.**

> **의존성 주입이 이루어지는 부분은 위 코드중 아래 부분이다.**

```java
private final MemberService memberService;
private final MemberMapper memberMapper;

public MemberController(MemberService memberService, MemberMapper memberMapper) {
	this.memberService = memberService;
	this.memberMapper = memberMapper;
}
```

각 메서드에서, DTO는 Member 클래스의 객채로 변환되어 MemberService 클래스의 메서드의 파라미터로 사용된다.

또한 생성자를 통해 의존성을 주입하였기 때문에 느슨한 결합으로 구성되어있다.

# **DTO 클래스와 엔티티 클래스의 역할 분리가 필요한 이유**

-   계층별 관심사의 분리
    -   기능에 대한 관심사가 다르다.
    -   하나의 클래스나 메서드 내에서 여러 개의 기능을 구현하는 것은 객체 지향 설계 관점에서 리팩토링 대상이다.
-   코드 구성의 단순화
    -   DTO 클래스에서 사용하는 유효성 검사 애너테이션이 Entity 클래스에서 사용된다면, JPA에서 사용하는 애너테이션과 뒤섞인 상태가 된다.
    -   이는 유지보수 관점에서 좋지 않은 설계이다.
-   REST API 스펙의 독립성 확보
    -   데이터 액세스 계층에서 전달 받은 데이터로 채워진 Entity 클래스를 그대로 응답으로 전달한다면, 원치 않는 데이터까지 전송될 수 있다. (ex. 유저의 패스워드 등)
    -   DTO 클래스를 사용하면 원하지 않는 데이터는 노출하지 않으면서, 원하는 데이터만 제공할 수 있다.

> **핵심 요약  
> \- 애플리케이션에 있어 Service는 도메인 업무 영역을 구현하는 비즈니스 로직을 처리하는 것을 의미  
> \- Controller 클래스는 @RestController 애너테이션을 사용해 Spring Bean으로 등록하고,  HTTP Response Body에 데이터를 담아 객체를 반환하도록 할 수 있다. (@Controller처럼 View에 Model을 담는 것 까지는 동일하다.  
> \- @Service 애너테이션은 @Component와 동일한 기능을 가지며, 해당 컴포넌트의 역할에 따라 부여한 것이다.  
>   (@Repository, @Controller 등도 같은 것으로 알고 있다.)  
> \- 생성자를 통한 DI의 경우 @Autowired를 생략할 수 있다. 용도에 따라 생성자가 여러 개일 경우 @Autowired 애너테이션을 사용해야 하지만, 그런 경우는 상당히 드물다.  
> \- Mapper 인터페이스를 통해 DTO 클래스와 Entity 클래스의 관심사를 분리할 수 있다.  
> \- Mapper는 개발자가 직접 구현할 수도 있지만, MapStruct 등의 라이브러리를 활용하는 것이 일반적이다.**
