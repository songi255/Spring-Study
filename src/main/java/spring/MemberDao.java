package spring;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// 컴포넌트 스캔
// 자동주입과 함께 사용하는 추가기능이다.
// 스프링이 직접 클래스를 검새해서 Bean으로 등록한다.
// 설정클래스에서 Bean으로 등록하지 않아도 자동으로 Bean으로 만들어진다는 뜻이다! (설정코드가 크게 줄어든다.) -> 근데 그러면 설정이 분산되지 않나?
//@Component
// 직접 Bean 이름을 지정할 수도 있다.
@Component("memberDao") //직접 이름을 줬지만, 주지 않으면 지금처럼 클래스명의 첫글자를 소문자로 바꿔 이름으로 사용한다.
public class MemberDao {
    // 이렇게 정의한 컴포넌트는 그냥 설정되는게 아니고, 설정클래스에서 컴포넌트스캔을 사용하겠다고(@ComponentScan) 명시해야 한다!
    private static long nextId = 0;

    private Map<String, Member> map = new HashMap<>();

    public Member selectByEmail(String email){
        return map.get(email);
    }

    public void insert(Member member){
        member.setId(++nextId);
        map.put(member.getEmail(), member);
    }

    public void update(Member member){
        map.put(member.getEmail(), member);
    }

    public Collection<Member> selectAll(){
        return map.values();
    }
}
