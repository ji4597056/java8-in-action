package com.study.java.concurrent;

import java.math.BigDecimal;

/**
 * @author Jeffrey
 * @since 2017/06/06 14:41
 */
public class ExpensiveComputable implements Computable<String, BigDecimal>{

    @Override
    public BigDecimal compute(String arg){
        return new BigDecimal(arg);
    }
}
