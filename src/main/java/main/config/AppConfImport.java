package main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import spring.MemberDao;
import spring.MemberPrinter;

@Configuration
// 2개 이상의 설정파일을 사용하는 또 다른방법이다.
//@Import(AppConf2.class) // 함께 사용할 클래스를 지정한다.
@Import({AppConf1.class, AppConf2.class}) // 이렇게 배열을 이용해서 2개 이상도 지정할 수 있다.
// 결국, 여러 설정파일을, 한 파일에서 Import로 모두 묶어주고, main에서는 이 클래스로만 ctx를 얻는 것이다.
public class AppConfImport {
    @Bean
    public MemberDao memberDao(){
        return new MemberDao();
    }

    @Bean
    public MemberPrinter memberPrinter(){
        return new MemberPrinter();
    }
}
