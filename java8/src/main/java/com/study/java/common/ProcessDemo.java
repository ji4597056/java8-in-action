package com.study.java.common;

import java.io.IOException;

/**
 * @author Jeffrey
 * @since 2017/06/29 15:24
 */
public class ProcessDemo {

    public static void main(String[] args) {
        try {
            Process p = new ProcessBuilder("java", "-v").start();
            while (p.isAlive()){
                System.out.println("~,~");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
