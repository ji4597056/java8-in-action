package com.study.java.design.chainOfResponsibility;

/**
 * @author Jeffrey
 * @since 16/05/2017 9:30 PM
 */
public abstract class Approver {

    protected String name;

    /**
     * 后继
     */
    protected Approver successor;

    public Approver(String name) {
        this.name = name;
    }

    public void setSuccessor(Approver successor) {
        this.successor = successor;
    }

    public abstract void handler(Integer day);
}
