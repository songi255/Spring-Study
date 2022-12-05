package spring;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class MemberRegisterService {
    // 의존대상을 구하는 가장 쉬운 방법은 직접 생성하는 것이다. 그러나 유지보수관점에서 문제가 생긴다.
    // private MemberDao memberDao = new MemberDao();

    // 다른 방법은 DI 혹은 service locator 이다. 이 중 Spring 과 관련된 것은 DI이다.
    // DI를 통해 의존객체를 전달받아보자.
    //private MemberDao memberDao;

    // 의존 자동주입을 사용한 예시
    @Autowired
    private MemberDao memberDao;

    // 의존객체를 직접 생성하지 않고, 생성자를 통해 받고있다.
    public MemberRegisterService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    // 의존 자동주입을 사용하면, 굳이 생성자에서 받을 필요가 없다.
    public MemberRegisterService() { }

    public Long regist(RegisterRequest req){
        // 이렇게, 다른 클래스의 메서드를 실행할 때 "의존한다"라고 표현한다.
        // "의존" 은 다시말해, 변경에 의해 영향을 받는 관계를 의미한다.
        Member member = memberDao.selectByEmail(req.getEmail());
        if (member != null){
            throw new DuplicateMemberException("dup email " + req.getEmail());
        }
        Member newMember = new Member(req.getEmail(), req.getPassword(), req.getName(), LocalDateTime.now());
        memberDao.insert(newMember);
        return newMember.getId();
    }
}
