package com.study.java.common;

import java.util.function.Supplier;

/**
 * @author Jeffrey
 * @since 2017/05/15 13:53
 */
public class SupplierDemo {

    public Integer getSize(final Supplier<String> supplier) {
        return supplier.get().length();
    }

    public static void main(String[] args) {
        SupplierDemo supplierDemo = new SupplierDemo();
        char key = 'D';
        System.out.println(supplierDemo.getSize(() -> {
            StringBuilder sb = new StringBuilder();
            sb.append('A');
            sb.append('B');
            sb.append('C');
            sb.append(key);
            return sb.toString();
        }));
    }
}
