package com.study.java.design.chainOfResponsibility;

/**
 * @author Jeffrey
 * @since 16/05/2017 9:41 PM
 */
public class Client {

    public static void main(String[] args) {
        Integer day = 8;
        Approver director = new Director("director");
        Approver vicePresident = new VicePresident("vicePresident");
        Approver president = new President("president");
        director.setSuccessor(vicePresident);
        vicePresident.setSuccessor(president);
        director.handler(day);
    }
}
