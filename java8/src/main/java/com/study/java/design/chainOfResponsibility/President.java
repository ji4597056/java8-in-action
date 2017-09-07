package com.study.java.design.chainOfResponsibility;

/**
 * @author Jeffrey
 * @since 16/05/2017 9:34 PM
 */
public class President extends Approver {

    public President(String name) {
        super(name);
    }

    @Override
    public void handler(Integer day) {
        if (day < 10) {
            System.out.println(this.getClass().getSimpleName() + " is doing.");
        } else {
            throw new RuntimeException("没有后继者！");
        }
    }
}
