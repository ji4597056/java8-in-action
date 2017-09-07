package com.study.java.flowable;


import org.flowable.engine.common.api.delegate.event.FlowableEvent;
import org.flowable.engine.common.api.delegate.event.FlowableEventListener;
import org.flowable.engine.common.api.delegate.event.FlowableEventType;
import org.flowable.engine.delegate.event.FlowableEngineEventType;

/**
 * @author Jeffrey
 * @since 2017/07/21 11:14
 */
public class MyEventListener implements FlowableEventListener {

    @Override
    public void onEvent(FlowableEvent event) {
        FlowableEventType type = event.getType();
        if (type.equals(FlowableEngineEventType.JOB_EXECUTION_SUCCESS)) {
            System.out.println("A job well done!");

        } else if (type.equals(FlowableEngineEventType.JOB_EXECUTION_FAILURE)) {
            System.out.println("A job has failed...");

        } else {
//            System.out.println("Event received: " + event.getType());
        }
    }

    @Override
    public boolean isFailOnException() {
        // The logic in the onEvent method of this listener is not critical, exceptions
        // can be ignored if logging fails...
        return false;
    }
}
