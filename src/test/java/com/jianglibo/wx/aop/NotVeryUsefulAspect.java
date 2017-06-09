package com.jianglibo.wx.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NotVeryUsefulAspect {

	/*
	 * execution(public * *(..))
	 * execution(* set*(..))
	 * execution(* com.xyz.service.AccountService.*(..))
	 * execution(* com.xyz.service.*.*(..))
	 * execution(* com.xyz.service..*.*(..))
	 * within(com.xyz.service.*)
	 * within(com.xyz.service..*)
	 * 
	 */
	
	@Pointcut("execution(* com.jianglibo.wx.aop.AspectTargetTtt.beAdvised(..)) && args(c,..)")// the pointcut expression
//	@Pointcut("execution(* com.jianglibo.wx.aop.AspectTargetTtt.*(..))")// the pointcut expression
	private void anyOldTransfer(String c) {}// the pointcut signature
//	private void anyOldTransfer() {}// the pointcut signature
	
    @Before("anyOldTransfer(c)")
    public void doBeforeAccessCheck(String c) {
        System.out.println("c");
//        System.out.println(retVal);
    }
    
    @AfterReturning(pointcut="anyOldTransfer(c)",returning="retVal")
    public void doAfterAccessCheck(String c, String retVal) {
        System.out.println("c");
        System.out.println(retVal);
    }
}
