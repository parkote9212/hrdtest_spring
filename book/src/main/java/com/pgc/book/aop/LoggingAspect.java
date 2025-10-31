package com.pgc.book.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    /**
     * 4. Pointcut (적용 지점) 정의
     * : "어디에" 이 AOP를 적용할 것인지 범위를 지정합니다.
     * "com.pgc.book.controller 패키지 하위의 모든 클래스의 모든 메서드"
     */

    @Pointcut("execution(* com.pgc.book.controller.*.*(..))")
    private void controllerPointcut(){}

    /**
     * 5. Around Advice (공통 기능)
     * : Pointcut(controllerPointcut())에 정의된 메서드들의
     * "실행 전, 실행 후, 예외 발생 시" 모두를 감쌀 수 있는 강력한 Advice입니다.
     *
     * @param joinPoint (실행되는 메서드 자체에 대한 정보)
     * @return 원본 메서드(Controller)가 반환하는 값
     * @throws Throwable 원본 메서드에서 발생할 수 있는 예외
     */
    @Around("controllerPointcut()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{

        //실행전 - 시작 시간 기록
        long startTime = System.currentTimeMillis();

        //실행 - 원본 메서드를 실행
        Object result = joinPoint.proceed();

        //실행후 - 종료 시간 기록 및 로그 출력
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        log.info("[Execution Time {}.{} --- {} ms", className, methodName, executionTime);

        return result;
    }
}
