package com.bar.infrastructure.web.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//TODO: Remake the aspect, right now is useless
@Aspect
@Component
public class NegativeIdAspect {

    private static final Logger logger = LogManager.getLogger(NegativeIdAspect.class);

    @Pointcut("execution(* com.bar.controller.BarController.getBarById(..)) && args(id)")
    private void getBarWithNegativeId(Long id) {}

    @Before("getBarWithNegativeId(id)")
    public void logNegativeId(Long id) {
        if (id < 0) {
            logger.info("Requesting bar with negative ID: {}}", id);
        }
    }
}
