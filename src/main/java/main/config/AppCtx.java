package main.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.*;

// Spring 을 사용한 DI. 설정정보를 이용해서 작성한다.
// 이렇게 Annotation을 사용한 경우 AnnotationConfigApplicationContext 를 사용해서 생성한다.
// Configuration 클래스는 내부적으로 Bean으로 생성되어 관리된다.
@Configuration
public class AppCtx {

    /* 의존자동주입의 순서는 어떻게 될까?
        단순하게 객체의 생명주기를 생각해보면 된다.
        객체 생성시
            - 필드 초기화
            - 생성자 호출
            - setter 호출
        순서로 진행되니, 뒤에 있는 자동주입에 의해 덧씌워지게 된다.
    * */

    @Bean
    public MemberDao memberDao(){
        return new MemberDao(); // new를 return 하지만, Spring이 알아서 싱글톤으로 return 한다.
        // 어떻게 가능할까? 설정클래스를 상속하여 새로운 클래스를 만들어서 사용하기 때문.
    }

    @Bean
    public MemberRegisterService memberRegSvc(){
        // 1. 생성자를 사용해서 주입한다.
        //return new MemberRegisterService(memberDao());
        return new MemberRegisterService(); // 의존 자동주입 사용
    }

    @Bean
    public ChangePasswordService changePwdSvc(){
        //ChangePasswordService pwdSvc = new ChangePasswordService();
        //pwdSvc.setMemberDao(memberDao()); // 2. setter를 통해서 주입한다.
        /* java Beans setter명명규칙
`           1. 파라미터 1개
            2. 리턴타입 void
        */
        // 두 DI 방식 차이는 없다. 파라미터가 많으면 setter가 가독성이 더 좋을것이고, 아니면 생성자가 더 좋을것이고..
        //return pwdSvc;
        return new ChangePasswordService(); // 의존 자동주입 사용
    }

    @Bean
    @Qualifier("printer")
    public MemberPrinter memberPrinter(){
        return new MemberPrinter();
    }

    @Bean
    public MemberSummaryPrinter memberPrinter2(){
        return new MemberSummaryPrinter();
    }

    @Bean
    public MemberListPrinter listPrinter(){
        //return new MemberListPrinter(memberDao(), memberPrinter());
        return new MemberListPrinter(); // 의존 자동주입을 사용
    }


    @Bean
    public MemberInfoPrinter infoPrinter(){
        MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
//        infoPrinter.setMemberDao(memberDao());
//        infoPrinter.setPrinter(memberPrinter());
        // 위 set 호출은 둘 다 Autowired 로 자동호출되어 주입되기 때문에 호출할 필요가 없다!!
        return infoPrinter;
    }

    @Bean
    public VersionPrinter versionPrinter(){
        VersionPrinter versionPrinter = new VersionPrinter();
        versionPrinter.setMajorVersion(5);
        versionPrinter.setMinorVersion(0);
        return versionPrinter;
    }

    @Bean
    //@Qualifier("printer") // 자동주입 가능한 Bean이 2개이상이기 떄문에, 자동주입을 지정한다.
    // 1. @Bean을 붙인 설정메서드에서 가능하다.
    // 2. 이렇게 지정한 한정값은 실제 의존이 자동주입되는 @Autowired 위치에서 한번 더 사용하여, 주입객체를 지정해 줄 수 있다.
    // 즉, @Qualifier 가 없으면 메서드 이름을 한정자로 사용하고, 있다면 여기서 지정한 값을 한정자로 사용한다.
    // @Autowired 에서 주입할 때에도 마찬가지로, @Qualifier 가 없다면 파라미터 이름을 한정자로 사용해서 Bean을 찾는다.
    public VersionPrinter oldVersionPrinter(){
        VersionPrinter versionPrinter = new VersionPrinter();
        versionPrinter.setMajorVersion(4);
        versionPrinter.setMinorVersion(3);
        return versionPrinter;
    }
}
