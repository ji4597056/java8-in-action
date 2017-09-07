package com.study.java.flowable;

/**
 * @author Jeffrey
 * @since 2017/07/26 16:07
 */
public enum FlowableEnums {

    DEVELOPER_SEND("developer_send"),

    TESTER_APPROVED("tester_approved"),

    TO_PRE_PRODUCER("to_preproducer"),

    PRE_PRODUCER_APPROVED("preproducer_approved"),

    PRODUCER_APPROVED("producer_approved"),

    DEVELOPER_ID("developer_id"),

    TESTER_ID("tester_id"),

    PRE_PRODUCER_ID("preproducer_id"),

    STARTER_ID("starter_id");

    private String key;

    FlowableEnums(String key) {
        this.key = key;
    }

    public String value() {
        return this.key;
    }

}
