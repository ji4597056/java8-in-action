package com.study.java.flowable;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * @author Jeffrey
 * @since 2017/07/20 16:36
 */
public class CallExternalSystemDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        System.out.println(
            "Calling the external system for employee " + execution.getVariable("employee"));
    }

}
