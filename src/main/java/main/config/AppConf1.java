package main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.MemberDao;
import spring.MemberPrinter;

//설정파일 2개 이상으로 나누기
@Configuration
public class AppConf1 {
    @Bean
    public MemberDao memberDao(){
        return new MemberDao();
    }

    @Bean
    public MemberPrinter memberPrinter(){
        return new MemberPrinter();
    }
}
