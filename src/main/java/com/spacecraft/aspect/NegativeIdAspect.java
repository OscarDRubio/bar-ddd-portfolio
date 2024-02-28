package com.spacecraft.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Aspect
@Component
public class NegativeIdAspect {

    private static final Logger logger = LogManager.getLogger(NegativeIdAspect.class);

    @Pointcut("execution(* com.spacecraft.controller.SpacecraftController.getSpacecraftById(..)) && args(id)")
    private void getSpacecraftWithNegativeId(Long id) {}

    @Before("getSpacecraftWithNegativeId(id)")
    public void logNegativeId(Long id) {
        if (id < 0) {
            logger.info("Requesting spacecraft with negative ID: {}}", id);
        }
    }
}
