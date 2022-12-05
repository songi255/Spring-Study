package aop.main;

import aop.config.AppCtx;
import aop.main.Calculator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainAspect {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);

        Calculator cal = ctx.getBean("calculator", Calculator.class);
        //RecCalculator cal = ctx.getBean("calculator", RecCalculator.class); 만약 이렇게, bean을 Rec 타입으로 바꾸면 어떻게 될까?
        /*  AppCtx에서 실제 반환하는 객체는 Rec 이었으니, Rec으로 받을 수 있을 것 처럼 보인다. 그러나 실제로는 Cal를 구현해서 Rec과 ExeTimeAspect를
            섞어 만든 $Proxy 객체이다. 즉, Rec이 아닌 Cal를 구현했으므로 Rec으로 바꿀 수 없다.
            이건 Spring 특성인데, 실제 생성되는 객체(Rec) 가 인터페이스(Calculator)를 상속했으면, 인터페이스를 이용해서 Proxy를 생성한다.
            이렇게 하고 싶지 않다면 AppCtx의 @Eanable~~에 (proxyTargetClass = true) 로 설정해주면 된다.
        *
        *
        * */


        long fiveFact = cal.factorial(5); // measure가 실행된다.
        System.out.println("cal.factorial(5) = " + fiveFact);
        System.out.println(cal.getClass().getName()); // RegCalculator가 찍히는게 아니고 $Proxy~~ 같은 객체가 찍힌다.
        ctx.close();
    }
}
