package com.study.java.lambda.test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/05/16 14:20
 */
public class TestDemo {

    @Test
    public void test1() {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
        nums.stream().map(num -> num * num).collect(Collectors.toList());

        List<Integer> nums1 = Arrays.asList(1, 2);
        List<Integer> nums2 = Arrays.asList(3, 4, 5);
        System.out.println(nums1.stream().flatMap(i -> nums2.stream().map(j -> new Integer[]{i, j}))
            .collect(Collectors.toList()));
    }

    @Test
    public void test2() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
        );

        // 找出2011年发生的所有交易，并按交易额排序（从低到高）。
        print(transactions.stream().filter(transaction -> transaction.getYear() == 2011).sorted(
            Comparator.comparing(Transaction::getValue)).collect(Collectors.toList()));
        // 交易员都在哪些不同的城市工作过？
        print(transactions.stream().map(transaction -> transaction.getTrader().getCity())
            .collect(Collectors.toSet()));
        // 查找所有来自于剑桥的交易员，并按姓名排序。
        print(transactions.stream()
            .filter(transaction -> Objects.equals(transaction.getTrader().getCity(), "Cambridge"))
            .map(Transaction::getTrader)
            .sorted(Comparator.comparing(Trader::getName)).collect(Collectors.toList()));
        // 返回所有交易员的姓名字符串，按字母顺序排序。
        print(transactions.stream().map(transaction -> transaction.getTrader().getName())
            .sorted(String::compareTo).collect(Collectors.joining()));
        // 有没有交易员是在米兰工作的？
        print(transactions.stream()
            .anyMatch(transaction -> Objects.equals(transaction.getTrader().getCity(), "Milan")));
        // 打印生活在剑桥的交易员的所有交易额。
        print(transactions.stream()
            .filter(transaction -> Objects.equals(transaction.getTrader().getCity(), "Cambridge"))
            .map(Transaction::getValue).reduce(0, Integer::sum));
        // 所有交易中，最高的交易额是多少？
        print(
            transactions.stream().map(Transaction::getValue).reduce(Integer::max));
        print(transactions.stream().collect(Collectors.summingInt(Transaction::getValue)));
        print(transactions.stream().collect(Collectors.averagingInt(Transaction::getValue)));
        // 找到交易额最小的交易。
        print(transactions.stream().reduce(
            (transaction1, transaction2) -> transaction1.getValue() < transaction2.getValue()
                ? transaction1 : transaction2));
        print(transactions.stream().min(Comparator.comparing(Transaction::getValue)));
    }

    @Test
    public void test3() {
        print(IntStream.rangeClosed(2, 100000).boxed().parallel()
            .collect(Collectors.partitioningBy(this::isPrime)));
    }

    private Boolean isPrime(Integer n) {
        int root = (int) Math.sqrt(n);
        return IntStream.rangeClosed(2, root).noneMatch(value -> n % value == 0);
    }

    private void print(Object obj) {
        System.out.println("===============================");
        long start = System.currentTimeMillis();
        System.out.println(obj);
        System.out.println("cost time:" + (System.currentTimeMillis() - start));
        System.out.println("===============================");
    }

    public class Transaction {

        private final Trader trader;
        private final int year;
        private final int value;

        public Transaction(Trader trader, int year, int value) {
            this.trader = trader;
            this.year = year;
            this.value = value;
        }

        public Trader getTrader() {
            return this.trader;
        }

        public int getYear() {
            return this.year;
        }

        public int getValue() {
            return this.value;
        }

        public String toString() {
            return "{" + this.trader + ", " +
                "year: " + this.year + ", " +
                "value:" + this.value + "}";
        }
    }

    public class Trader {

        private final String name;
        private final String city;

        public Trader(String n, String c) {
            this.name = n;
            this.city = c;
        }

        public String getName() {
            return this.name;
        }

        public String getCity() {
            return this.city;
        }

        public String toString() {
            return "Trader:" + this.name + " in " + this.city;
        }
    }
}
