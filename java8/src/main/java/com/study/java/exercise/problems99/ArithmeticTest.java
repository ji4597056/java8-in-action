package com.study.java.exercise.problems99;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/06/09 9:32
 */
public class ArithmeticTest {

    @Test
    public void shouldSay7IsAPrimeNumber() throws Exception {
        boolean prime = isPrime(7);
        assertTrue(prime);
    }

    @Test
    public void shouldSay10IsNotAPrimeNumber() throws Exception {
        boolean prime = isPrime(10);
        assertFalse(prime);
    }

    // 31
    private boolean isPrime(int i) {
        return IntStream.rangeClosed(2, (int) Math.ceil(new Double(Math.sqrt(i))))
            .noneMatch(j -> i % j == 0);
    }

    @Test
    public void shouldFindPrimeFactorsOf315() throws Exception {
        List<Integer> primeFactors = primeFactors(315);
        System.out.println(primeFactors);
        assertThat(primeFactors, hasItems(3, 3, 5, 7));
    }

    @Test
    public void shouldFindPrimeFactorsOf33() throws Exception {
        List<Integer> primeFactors = primeFactors(33);
        assertThat(primeFactors, hasItems(3, 11));
    }

    // 32
    private List<Integer> primeFactors(int prime) {
        List<Integer> result = new ArrayList<>();
        while (prime > 1) {
            for (int i = 2; i <= prime; i++) {
                if (prime % i == 0) {
                    result.add(i);
                    prime = prime / i;
                    break;
                }
            }
        }
        return result;
    }

    @Test
    public void shouldFindPrimeFactorsOf315_33() throws Exception {
        List<SimpleEntry<Integer, Integer>> primeFactors = primeFactorsMult(315);
        assertThat(primeFactors,
            hasItems(new SimpleEntry<>(3, 2), new SimpleEntry<>(5, 1), new SimpleEntry<>(7, 1)));
    }

    @Test
    public void shouldFindPrimeFactorsOf33_33() throws Exception {
        List<SimpleEntry<Integer, Integer>> primeFactors = primeFactorsMult(33);
        assertThat(primeFactors, hasItems(new SimpleEntry<>(3, 1), new SimpleEntry<>(11, 1)));
    }

    // 33
    private List<SimpleEntry<Integer, Integer>> primeFactorsMult(int prime) {
        List<SimpleEntry<Integer, Integer>> result = new ArrayList<>();
        primeFactors(prime).stream().collect(Collectors.groupingBy(o -> o)).forEach(
            (integer, integers) -> result
                .add(new SimpleEntry<Integer, Integer>(integer, integers.size())));
        return result;
    }

    @Test
    public void shouldGiveAllPrimeNumbersBetween2And10() throws Exception {
        List<Integer> primeNumbers = primeNumbers(IntStream.rangeClosed(2, 10));
        assertThat(primeNumbers, hasSize(4));
        assertThat(primeNumbers, hasItems(2, 3, 5, 7));
    }

    @Test
    public void shouldGiveAllPrimeNumbersBetween7And31() throws Exception {
        List<Integer> primeNumbers = primeNumbers(IntStream.rangeClosed(7, 31));
        assertThat(primeNumbers, hasSize(8));
        assertThat(primeNumbers, hasItems(7, 11, 13, 17, 19, 23, 29, 31));
    }

    // 34
    private List<Integer> primeNumbers(IntStream intStream) {
        return intStream.filter(i -> isPrime(i)).boxed().collect(Collectors.toList());
    }

    @Test
    public void _8_isthesumof_3_and_5() throws Exception {
        List<Integer> numbers = goldbach(8);
        assertThat(numbers, hasSize(2));
        assertThat(numbers, hasItems(3, 5));
    }

    @Test
    public void _28_isthesumof_5_and_23() throws Exception {
        List<Integer> numbers = goldbach(28);
        assertThat(numbers, hasSize(2));
        assertThat(numbers, hasItems(5, 23));
    }

    // 35
    private List<Integer> goldbach(int i) {
        int first = IntStream.rangeClosed(2, (int) Math.ceil(new Double(Math.sqrt(i))))
            .filter(value -> isPrime(value) && isPrime(i - value)).findFirst().getAsInt();
        return Arrays.asList(first, i - first);
    }

    @Test
    public void shouldProduceAListOfGoldbachCompositions() throws Exception {
        List<SimpleEntry<Integer, List<Integer>>> compositions = goldbach_list1(
            IntStream.rangeClosed(9, 20));
        assertThat(compositions, hasSize(6));
        assertThat(compositions, hasItems(
            new SimpleEntry<>(10, Arrays.asList(3, 7)),
            new SimpleEntry<>(12, Arrays.asList(5, 7)),
            new SimpleEntry<>(14, Arrays.asList(3, 11)),
            new SimpleEntry<>(16, Arrays.asList(3, 13)),
            new SimpleEntry<>(18, Arrays.asList(5, 13)),
            new SimpleEntry<>(20, Arrays.asList(3, 17))
        ));
    }

    // 36
    private List<SimpleEntry<Integer, List<Integer>>> goldbach_list1(IntStream intStream) {
        return null;
    }

    @Test
    public void shouldProduceAListOfGoldbachCompositionsWhereBothPrimeNumbersAreGreaterThan50()
        throws Exception {
        List<SimpleEntry<Integer, List<Integer>>> compositions = goldbach_list(
            IntStream.rangeClosed(1, 2000), 50);
        assertThat(compositions, hasSize(4));
        assertThat(compositions, hasItems(
            new SimpleEntry<>(992, Arrays.asList(73, 919)),
            new SimpleEntry<>(1382, Arrays.asList(61, 1321)),
            new SimpleEntry<>(1856, Arrays.asList(67, 1789)),
            new SimpleEntry<>(1928, Arrays.asList(61, 1867))
        ));
    }

    // 36
    private List<SimpleEntry<Integer, List<Integer>>> goldbach_list(IntStream intStream, int i) {
        return null;
    }

    @Test
    public void gcdOf36And63Is9() throws Exception {
        int gcd = gcd(36, 63);
        assertThat(gcd, equalTo(9));
    }

    // 37
    private int gcd(int i, int j) {
        return IntStream.rangeClosed(2, (int) Math.ceil(new Double((Math.min(i, j)) / 2)))
            .filter(value -> i % value == 0 && j % value == 0).max().getAsInt();
    }

    @Test
    public void shouldSay35And64IsCoprime() throws Exception {
        boolean coprime = coprime(35, 64);
        assertTrue(coprime);
    }

    // 38
    private boolean coprime(int i, int j) {
        return IntStream.rangeClosed(2, (int) Math.ceil(new Double((Math.min(i, j)) / 2)))
            .noneMatch(value -> i % value == 0 && j % value == 0);
    }

    @Test
    public void shouldSayPhiOf10Is4() throws Exception {
        long phi = phi(10);
        assertThat(phi, equalTo(4L));
    }

    // 39
    private long phi(int i) {
        return 0L;
    }
}
