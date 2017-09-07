package com.study.java.concurrent.disruptor;

import com.lmax.disruptor.WorkHandler;

/**
 * @author Jeffrey
 * @since 2017/06/19 15:45
 */
public class Consumer implements WorkHandler<PCData> {

    @Override
    public void onEvent(PCData event) throws Exception {
        System.out.println(Thread.currentThread().getId() + ":Event: --" + event.getValue() + "==");
    }
}
