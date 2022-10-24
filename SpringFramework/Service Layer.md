> **흐름 잡기**  
> **\[Spring\] DTO 포스트에서는 '클라이언트의 요청을 API 계층으로 받아오는 과정'을 다뤘다면**  
> **이번 포스트에서는 'API 계층으로 받아온 데이터를 서비스 계층에서 사용하기 위한 방법' 과 그 과정에서 필요한 '매핑' 에 대해 정리해볼 것이다.**  
> **\- 기반 지식 : Dependecy Injection**

# **API 계층과 서비스 계층의 연동(feat.DI)**

> **API 계층과 서비스 계층을 연동한다는 말은, API계층에서 구현한 Controller 클래스가 서비스 계층의 Service 클래스와 메서드 호출을 통해 상호작용 한다는 것을 의미한다.**

[##_Image|kage@b8WxNo/btrPrmmUX4I/kAig3QBNYoDArdlcPMcRrK/img.png|CDM|1.3|{"originWidth":152,"originHeight":110,"style":"alignCenter","caption":"기존 member 패키지"}_##]

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
