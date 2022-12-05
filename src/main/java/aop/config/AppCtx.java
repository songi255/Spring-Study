package aop.config;

import aop.main.Calculator;
import aop.aspect.ExeTimeAspect;
import aop.main.RecCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy // 이 어노테이션을 추가하면 @Aspect가 붙은 Bean을 찾아서 @Pointcut과 @Around를 사용한다.
//@EnableAspectJAutoProxy(proxyTargetClass = true) // 이렇게 하면 Interface가 아닌, 실제 제작객체(Rec)을 이용해서 프록시를 생성한다.
// 즉, Aspect를 사용하려면 설정파일에서 이거 추가하라고.
public class AppCtx {
    /* 이제 여기서 aop를 설정한다. */
    @Bean // 이 객체가 @Aspect가 붙은 객체였다. Bean으로 만들고 있다.
    public ExeTimeAspect exeTimeAspect(){ // 이놈만 뺴면 aop 빼고 전부 정상작동한다. 즉 모듈적으로 넣었다 뺐다 할 수 있다.
        return new ExeTimeAspect();
    }

    @Bean
    public Calculator calculator(){
        return new RecCalculator();
    }
}
