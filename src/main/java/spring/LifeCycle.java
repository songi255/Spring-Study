package spring;


/* Spring 생명주기
     1. new ctx로 컨테이너 초기화
        - bean 객체의 라이프사이클
            1. 객체 생성
            2. 의존설정
            3. 초기화
            4. 소멸
     2. 컨테이너에서 빈 객체 구해 사용
     3. 컨테이너 종료
*/

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

// bean 객체를 초기화, 소멸시키는 방법은, 아래 두 인터페이스와 각각의 메서드가 정의한다. (스프링에 의한 자동실행)
// 대표적인 예시가 DB, 채팅 클라이언트 등이다.
public class LifeCycle implements InitializingBean, DisposableBean {

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}

// 그러나, 외부에서 제공받은 클래스같은 경우, 마음대로 확장할 수 없다. 이럴 때는 어떻게 해야할까?
// 아무 커스텀 메서드를 이 객체에 정의한다.
// @Configuration에서 bean 등록시 @Bean(initMethod = "커스텀메서드이름", destroyMethod = "커스텀 메서드 이름") 으로 직접 지정가능하다. (매개변수는 없어야된다. 있으면 예외)
// 머.. init 같은건 걍 직접 해줘도 되고.

//TODO Scope
// @Bean
// @Scope("범위") -> 이런 방식으로 bean 설정시 스코프를 정할 수 있다.
//  - singleton : 기존값이다.
//  - prototype : 빈도는 낮다. bean객체를 get 할때마다 새로운 객체를 생성한다.
//      - 이때, prototype 스코프의 bean은 완전한 라이프사이클을 갖지 않는다. 무슨말이냐면, 컨테이너가 종료되어도 소멸메서드가 실행되지 않는다.
//      - 즉, 소멸처리를 직접 해야된다. -> 생명주기를 스스로 관리해야한다는 뜻이다.