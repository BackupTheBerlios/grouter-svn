package org.grouter.common.logging;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Can be declared in context-aop.xml as an aspect or with annotations which requires
 * <pre>
 *    <aop:aspectj-autoproxy/>
 * </pre>
 * declared in context-aop.xml.
 *
 *
 * @author Georges Polyzois
 */
@Aspect
public class MethodLogger
{
    private static Log log = LogFactory.getLog(MethodLogger.class);

    /**
     * Starts a StopWatch and then calls underlying method which is weaved around and then tops the StopWatch
     * and prints out call time and method signature and return value.
     * @param joinPoint inn Spring AOP, a join point always represents a method execution.
     * @return
     * @throws Throwable
     */
    @Around("org.grouter.domain.dao.*.*(..)")
    public Object logAroundMethod(ProceedingJoinPoint joinPoint) throws Throwable
    {
        log.info("Starting stopwatch");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object[] args = joinPoint.getArgs();
        // E.g. public abstract org.grouter.domain.entities.User org.grouter.domain.service.UserService.findById(java.lang.Long)
        String name = joinPoint.getSignature().toLongString();
        StringBuffer sb = new StringBuffer(name + " called with: [");
        for (int i = 0; i < args.length; i++)
        {
            Object o = args[i];
            sb.append(o);
            sb.append((i == args.length - 1) ? "]" : ", ");
        }
        log.info(sb);
        Object retVal = joinPoint.proceed();
        stopWatch.stop();


        log.info(" return: " + retVal + ", time: " + stopWatch.getTime());
        return retVal;
    }

    

}
