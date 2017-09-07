package com.study.java.concurrent.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author Jeffrey
 * @since 2017/06/19 15:49
 */
public class PCDataFactory implements EventFactory<PCData>{

    @Override
    public PCData newInstance() {
        return new PCData();
    }
}
