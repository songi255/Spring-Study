package aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

import java.util.Arrays;

@Aspect // Aspect로 사용할 객체에 붙여준다.
public class ExeTimeAspect {
    /* 스프링에서 공통기능을 구현하는것은 단순하다.
    *   1. Aspect로 사용할 클래스에 @Aspect를 붙인다.
    *   2. @Pointcut 정의
    *   3. 공통기능 구현한 메서드에 @Around 붙임
    *   즉, Aspect 구현클래스 만들고, 자바설정으로 어디에 적용할 지 설정.
    *  */

    @Pointcut("execution(public * aop.main.*(..))") // aop - main 패키지나 그 하위에 속한 Bean의 public 메서드를 설정한다는 뜻.
    private void publicTarget(){ // 메서드는 그냥 허울인가? 잘 모르겠다. 여튼 어떤 메서드들에 적용할지를 Pointcut으로 정의하고, 그 내용을 Around로 적는다.
        /*  execution 명시자 표현식
            ( [수식어] 리턴타입 [클래스이름]메서드이름(파라미터) ) 가 형식이다.
                - 수식어는 public, protected인데, 스프링 AOP는 차피 public 메서드에만 적용할 수 있어 사실상 public 만 의미있다.
                - * = 모든 값 / .. = 0개 이상
                - ex)
                    - public void set*(..) -> return 이 void 이고 이름이 set~~~인, 매개변수가 0개 이상인 것
                        - class 이름이 생략되있는 형태
                    - * chap07.*.*(..) -> 모든 return 타입, chap07 -> 모든 클래스 -> 모든 메서드(매개변수 0개 이상) 에 적용
                    - * chap07..*.*(..) -> chap07~~~(.main 같은..) 처럼, chap07 밑 모든 하위 패키지포함, 메서드 호출
                    - * get*(Integer, *) -> 모든 return 타입, get으로 시작하는 메서드 중 파라미터가 2개이고 첫번째건 Integer 타입
         */
    }

    // 아래 @Around가 붙은 메서드가 공통실행내용을 담고있다. 이 내용은 @Around에서 적어놓은 pointcut에 적용된다.
    @Around("publicTarget()") // publicTarget()에 정의해놓은 @pointcut에 공통기능을 적용하겠다는 뜻.
    // 사실, @Around(~~~)으로 execution 명시자를 직접 지정할수도 있다. 다만 여러 Advice가 동일한 Pointcut을 사용한다면 위에서처럼 @pointcut으로 뺸다.
    // 여기선 private로 했지만 public으로 설정하면 다른 클래스에서도 (classpath~~).publicTarget 으로 설정할 수 있다.
    // 즉, pointcut이 중복이 많이되면 아예 Pointcut만 따로 빼서 클래스를 만들어 사용하면 관리가 편하다. (Bean으로 등록할 필요는 없다.)
    @Order(1) // @Around는 동일 pointcut에 여러개 중복적용할 수 있다. 이런 경우 순서가 중요하다면 Order로 지정할 수 있따. 작은 순서대로 실행.
    // Order 지정하지 않으면 실행순서는 Spring이나 Java 버전에 따라 달라질 수 있다.
    public Object measure(ProceedingJoinPoint joinPoint) throws Exception{
        /* jointpoint 매개변수는 proceed()를 이용해서 프록시 대상객체의 메서드를 호출할 때 사용한다. */
        long start = System.nanoTime();
        try {
            Object result = joinPoint.proceed();
            return result;
        } catch (Throwable e) {
            //e.printStackTrace();
        } finally {
            long finish = System.nanoTime();
            Signature sig = joinPoint.getSignature(); // 호출메서드의 시그니처(return이나 exception은 미포함)
            System.out.printf("%s.%s(%s) 실행시간 : %d ns\n",
                    joinPoint.getTarget().getClass().getSimpleName(),
                    sig.getName(), Arrays.toString(joinPoint.getArgs()), // 호출대상객체, 인자목록 등을 구할 수 있다.
                    (finish - start)
            );
        }
        return null;
    }
}
