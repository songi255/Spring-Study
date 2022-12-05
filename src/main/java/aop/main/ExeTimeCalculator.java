package aop.main;

import aop.main.Calculator;

public class ExeTimeCalculator implements Calculator {

    private Calculator delegate;

    public ExeTimeCalculator(Calculator delegate) {
        this.delegate = delegate;
    }

    @Override
    public long factorial(long num) {
        long start = System.nanoTime();
        long result = delegate.factorial(num);
        long end = System.nanoTime();

        System.out.printf("%s.factorial(%d) 실행시간 = %d\n", delegate.getClass().getSimpleName(), num, (end - start));

        return result;

        /* 이렇게, 핵심기능(calculate)은 다른객체에 위임하고 부가적인 기능을 제공하는 객체를 프록시라고 부른다.
        * 사실, 이 경우에는 엄밀히 프록시(접근제어관점)보다 데코레이터(기능추가)에 가깝다.
        *
        * 여튼, 이렇게 공통기능구현과 핵심기능구현을 분리하는 것이 AOP의 핵심이다. (재사용성을 높인다.)
        * 이렇게 공통기능삽입에는 3가지 방법이 있는데, 1. 컴파일시점에 삽입 / 2. 클래스 로딩시점에 바이트코드에 삽입 / 3. 런타임에 프록시 객체 사용
        * 1, 2번은 Spring AOP에서 지원하지 않고, AspectJ같은 AOP 전용도구를 사용해서 적용할 수 있다.
        * 결론적으로 Spring이 제공하는 AOP 방식은 3번째 방식이다. AOP객체를 자동으로 만들어줘서, 현재 이 Exe 클래스를 만들 필요가 없다.
        *
        * AOP 주요 용어정리
        *   - Aspect
        *       - 공통기능을 의미. (트랜잭션, 보안 등)
        *   - Advice
        *       - 언제 공통 관심기능을 핵심로직에 적용할 지 정의
        *       - 예를 들어, "메서드 호출 전(언제)", "트랜잭션시작(공통기능)" 기능을 적용한다.
        *       - spring proxy의 구현가능한 Advice 종류
        *           - Before Advice : 메소드 호출 전
        *           - After Returning Advice
        *           - After Throwing Advice
        *           - After Advice : Exception 발생여부에 상관없이 무조건 실행. try - catch - finally의 finally에 해당
        *           - Around Advice : 실행전, 후 또는 Exception 발생시점에 실행??
        *               - 이놈이 가장 널리 사용된다. 이유는 다양한 시점에 원하는 기능을 삽입할 수 있기 때문이다.
        *               - 캐시, 성능모니터링 등 Aspect를 구현할때는 이것을 주로 이용
        *   - JointPoint
        *       - Advice 적용가능한 지점
        *       - 메서드 호출, 필드값 변경 등이 해당
        *       - 스프링은 proxy를 이용하므로, 메서드호출에 대한 Jointpoint만 지원
        *   - Pointcut
        *       - Jointpoint의 부분집합. 실제 Advice가 적용되는 Jointpoint를 나타냄
        *       - 정규표현식이나 AspectJ의 문법을 이용해서 정의가능
        *   - Weaving
        *       - Advice를 핵심로직코드에 적용하는 것
        *
        * 이제, ExeTimeAspect 파일에서 확인해보자.
        * */
    }
}
