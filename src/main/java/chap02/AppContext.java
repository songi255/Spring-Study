package chap02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 이 클래스는 Spring 설정클래스로 사용한다.

// @Configuration 는 해당 클래스를 스프링설정클래스로 지정한다.
@Configuration
public class AppContext {

    // @Bean 이 핵심이다.
    // Bean 객체 : Spring 이 생성하고 관리하는 객체
    @Bean
    public Greeter greeter(){ // 메서드의 이름은 bean 식별자가 된다.
        Greeter g = new Greeter();
        g.setFormat("%s, 안녕하세요!");
        return g;
    }


}
