package chap02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        // 생성했던 Spring 설정 클래스 AppContext를, 스프링이 제공하는 클래스를 이용해서 읽어와서 사용하는 것이다.
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class); //AppContext가 우리가 작성한 설정파일이다.
        // 이때 설정파일을 읽어 초기화하므로, 이때 이미 bean들이 초기화되있다.
        Greeter g = ctx.getBean("greeter", Greeter.class); // 설정파일에서 정의했던 bean 메소드 이름을 식별자로 사용하고있다.
        // 별도 설정을 하지 않으면 Bean 은 싱글톤이다.
        // 두번째 매개변수는 검색할 bean 타입이다.
        /* TODO 이처럼, 스프링의 핵심기능은 객체를 생성하고 초기화하는 것이다.
            관련 기능은 ApplicationContext 인터페이스에 정의되어있다.
            BeanFactory : Interface
                - 객체 생성, 검색에 대한 기능 정의
                - getBean(), 싱글톤/프로토타입 bean 여부 확인 등
                - ListableBeanFactory : Interface
                    - ApplicationContext : Interface
                        - 메시지, 프로필 / 환경변수 등 처리
                        - Bean의 생성, 초기화, 보관, 제거 등을 관리하므로, Container 라고도 부른다. (Spring Container 라고 표현)
                        - 내부적으로 이름-객체 연결하는 정보를 가진다.
                        - ConfigurableApplicationContext : Interface
                            - AbstractApplicationContext : Abstract Class
                            - BeanDefinitionRegistry : Interface
                                - AnnotationConfigRegistry : Interface
                                - GenericApplicationContext : Class, 둘 다 상속
                                    - 실제 구현 클래스 3가지
                                    - 결국 아래 3가지 클래스는 모두 Bean 생성하고 내부에 보관하는 기능을 가진다.
                                    - GenericXmlApplicationContext
                                        - XML 이용
                                    - GenericGroovyApplicationContext
                                        - Groovy 이용
                                    - AnnotationConfigApplicationContext : 둘 다 상속
                                        - 애노테이션 이용
         */

        String msg = g.greet("스프링");
        System.out.println(msg);

        ctx.close();
    }
}
