package com.study.java.design.chainOfResponsibility;

/**
 * @author Jeffrey
 * @since 16/05/2017 9:34 PM
 */
public class Director extends Approver {

    public Director(String name) {
        super(name);
    }

    @Override
    public void handler(Integer day) {
        if (day < 3) {
            System.out.println(this.getClass().getSimpleName() + " is doing.");
        } else {
            successor.handler(day);
        }
    }
}
