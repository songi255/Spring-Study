package aop.main;

import aop.main.Calculator;

public class RecCalculator implements Calculator {
    @Override
    public long factorial(long num) {
        if (num == 0)
            return 1;
        else
            return num * factorial(num - 1);
        /* factorial을 재귀로 구현했다. 이 함수의 실행시간을 알고싶으면 어떻게 해야할까? 이때 등장하는 것이 프록시객체이다. */
    }
}
