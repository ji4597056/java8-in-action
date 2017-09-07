package com.study.java.flowable;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * @author Jeffrey
 * @since 2017/07/20 16:43
 */
public class SendRejectionMail implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        System.out.println(
            "Sending rejection mail to employee " + execution.getVariable("employee"));
    }
}
