package io.fdlessard.codebites.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


/**
 * Created by fdlessard on 16-08-07.
 */

@Component
@Aspect
public class AopLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopLogger.class);

    @Around("execution(* com.lessard.codesamples..*.*(..))")
    public Object timeMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object retVal = joinPoint.proceed();

        stopWatch.stop();

        StringBuffer logMessage = new StringBuffer();
        logMessage.append(joinPoint.getTarget().getClass().getSimpleName());
        logMessage.append(".");
        logMessage.append(joinPoint.getSignature().getName());
        logMessage.append("(");

        Object[] signatureArgs = joinPoint.getArgs();

        String argListStr = StringUtils.join(signatureArgs, ", ");

        logMessage.append(argListStr);
        logMessage.append(") - ");

        logMessage.append("Execution time: ");
        logMessage.append(stopWatch.getTotalTimeMillis());
        logMessage.append(" ms");

        LOGGER.info(logMessage.toString());

        return retVal;
    }

}
