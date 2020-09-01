package com.tieto.tictactoesolution.Aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.logging.Logger;

//TODO: CONFIGURATION OF THIS FILE, LOOK AT BOOKMARKED
@Aspect
public class MyLogger {

    private Logger log = Logger.getLogger(getClass().getName());

    @Before("execution(*     com.tieto.tictactoesolution.Strategies.StrategyMovement.moveWithoutStrategy(..)")
    public void log() {
        log.warning("I HAVE BEEN MOVED WITHOUT A STRATEGY THAT IS NOT REALLY GOOD");
    }
}
