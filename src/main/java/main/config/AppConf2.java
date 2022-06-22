package main.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.*;

@Configuration
public class AppConf2 {
    // 해당 타입의 Bean을 찾아서 필드에 할당한다,
    // 만약 (상속포함) 해당타입이 여러개라면 오류가 발생한다. -> @Qualifier 애노테이션으로 해결해야 한다.
    // @Configuration에서만 사용하는것은 아니고, 일반 Bean에서도 사용할 수 있다.
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private MemberPrinter memberPrinter;
    // 이를 "의존자동주입", Spring boot가 나오면서 이걸 사용하는 추세이다.
    // @Resource 를 사용할 수도 있다.
    // @Inject 도 지원한다. 하지만 셋중에서 @Autowired 를 제일 많이 사용한다.

    @Bean
    public MemberRegisterService memberRegSvc(){
        // 1. 생성자를 사용해서 주입한다.
        return new MemberRegisterService(memberDao);
    }

    @Bean
    public ChangePasswordService changePwdSvc(){
        ChangePasswordService pwdSvc = new ChangePasswordService();
        pwdSvc.setMemberDao(memberDao); // 2. setter를 통해서 주입한다.
        /* java Beans setter명명규칙
`           1. 파라미터 1개
            2. 리턴타입 void
        */
        // 두 DI 방식 차이는 없다. 파라미터가 많으면 setter가 가독성이 더 좋을것이고, 아니면 생성자가 더 좋을것이고..
        return pwdSvc;
    }

    @Bean
    public MemberListPrinter listPrinter(){
        return new MemberListPrinter(memberDao, memberPrinter);
    }

    @Bean
    public MemberInfoPrinter infoPrinter(){
        MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
        infoPrinter.setMemberDao(memberDao);
        infoPrinter.setPrinter(memberPrinter);
        return infoPrinter;
    }

    @Bean
    public VersionPrinter versionPrinter(){
        VersionPrinter versionPrinter = new VersionPrinter();
        versionPrinter.setMajorVersion(5);
        versionPrinter.setMinorVersion(0);
        return versionPrinter;
    }
}
