package com.study.java.exercise.problems99;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/05/22 9:39
 */
public class ListTest {

    @Test
    public void shouldFindLastElementFromAListOfAlphabets() throws Exception {
        assertThat(last(asList("a", "b", "c", "d")), is(equalTo("d")));
    }

    @Test
    public void shouldFindSecondLastElementFromAList() throws Exception {
        List<Integer> numbers = asList(1, 2, 11, 4, 5, 8, 10, 6);
        assertThat(secondLast(numbers), is(equalTo(10)));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowExceptionWhenListEmpty() throws Exception {
        secondLast(Collections.emptyList());
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowExceptionWhenListHasSingleElement() throws Exception {
        secondLast(Arrays.asList(1));
    }

    @Test
    public void shouldFindKthElementFromAList() throws Exception {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        assertThat(kth(numbers, 2), is(equalTo(3)));
    }

    @Test
    public void listOfEmptyListShouldBe0() throws Exception {
        int length = length(Collections.emptyList());
        assertThat(length, is(equalTo(0)));
    }

    @Test
    public void shouldFindListOfNonEmptyList() throws Exception {
        assertThat(length(Arrays.asList(1, 2, 3, 4, 5)), is(equalTo(5)));
    }

    @Test
    public void shouldReverseAList() throws Exception {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        assertThat(reverse(numbers), is(equalTo(Arrays.asList(5, 4, 3, 2, 1))));
    }

    @Test
    public void shouldReturnTrueWhenListIsPalindrome() throws Exception {
        assertTrue(isPalindrome(Arrays.asList("x", "a", "m", "a", "x")));
    }

    @Test
    public void shouldReturnFalseWhenListIsNotPalindrome() throws Exception {
        assertFalse(isPalindrome(Arrays.asList(1, 2, 3, 4, 5)));
    }

    @Test
    public void shouldFlattenAListOfList() throws Exception {
        List<String> flatten = flatten(asList("a", asList("b", asList("c", "d")), "e"),
            String.class);
        assertThat(flatten, hasSize(5));
        assertThat(flatten, hasItems("a", "b", "c", "d", "e"));
    }

    @Test
    public void shouldFlattenDeepNestedLists() throws Exception {
        List<String> flatten = flatten(
            asList("a", asList("b", asList("c", asList("d", "e", asList("f", "g"))), "h")),
            String.class);
        assertThat(flatten, hasSize(8));
        assertThat(flatten, hasItems("a", "b", "c", "d", "e", "f", "g", "h"));
    }

    @Test
    public void shouldReturnEmptyListWhenTryingToFlattenAnEmptyList() throws Exception {
        List<String> flatten = flatten(Collections.emptyList(), String.class);
        assertTrue(flatten.isEmpty());
    }

    @Test
    public void shouldRemoveConsecutiveDuplicatesInAList() throws Exception {
        List<String> compressedList = compress(
            asList("a", "a", "a", "a", "b", "c", "c", "d", "e", "e", "e", "e"));
        assertThat(compressedList, hasSize(5));
        assertThat(compressedList, hasItems("a", "b", "c", "d", "e"));
    }

    @Test
    public void shouldNotRemoveNonConsecutiveSimilarElementsFromAList() throws Exception {
        List<String> compressedList = compress(
            asList("a", "a", "a", "a", "b", "c", "c", "a", "a", "d", "e", "e", "e", "e"));
        assertThat(compressedList, hasSize(6));
        assertThat(compressedList, hasItems("a", "b", "c", "a", "d", "e"));
    }

    @Test
    public void shouldReturnAListWithTwoListElementsWhenAListWithTwoUniqueElementsIsPassed()
        throws Exception {
        List<List<String>> packedList = pack(Arrays.asList("a", "b"));
        assertThat(packedList, hasSize(2));
        assertThat(packedList.get(0), hasItems("a"));
        assertThat(packedList.get(1), hasItems("b"));
    }


    @Test
    public void shouldPackConsecutiveDuplicatesInTheirOwnLists() throws Exception {
        List<List<String>> packedList = pack(
            Arrays.asList("a", "a", "a", "a", "b", "c", "c", "a", "a", "d", "e", "e", "e", "e"));
        assertThat(packedList, hasSize(6));
        assertThat(packedList.get(0), hasItems("a", "a", "a", "a"));
        assertThat(packedList.get(1), hasItems("b"));
        assertThat(packedList.get(2), hasItems("c", "c"));
        assertThat(packedList.get(3), hasItems("a", "a"));
        assertThat(packedList.get(4), hasItems("d"));
        assertThat(packedList.get(5), hasItems("e", "e", "e", "e"));
    }

    @Test
    public void shouldEncodeAList() throws Exception {
        List<SimpleEntry<Integer, String>> encodedList = encode(
            Arrays.asList("a", "a", "a", "a", "b", "c", "c", "a", "a", "d", "e", "e", "e", "e"));
        System.out.println(encodedList);
        assertThat(encodedList, hasSize(6));
        assertThat(encodedList.get(0), is(equalTo(new SimpleEntry<>(4, "a"))));
        assertThat(encodedList.get(1), is(equalTo(new SimpleEntry<>(1, "b"))));
        assertThat(encodedList.get(2), is(equalTo(new SimpleEntry<>(2, "c"))));
        assertThat(encodedList.get(3), is(equalTo(new SimpleEntry<>(2, "a"))));
        assertThat(encodedList.get(4), is(equalTo(new SimpleEntry<>(1, "d"))));
        assertThat(encodedList.get(5), is(equalTo(new SimpleEntry<>(4, "e"))));
    }

    @Test
    public void shouldEncodeAList_11() throws Exception {
        List<Object> encodedList = encode_modified(
            Arrays.asList("a", "a", "a", "a", "b", "c", "c", "a", "a", "d", "e", "e", "e", "e"));
        System.out.println(encodedList);
        assertThat(encodedList, hasSize(6));
        assertThat(encodedList.get(0), is(equalTo(new SimpleEntry<>(4, "a"))));
        assertThat(encodedList.get(1), is(equalTo("b")));
        assertThat(encodedList.get(2), is(equalTo(new SimpleEntry<>(2, "c"))));
        assertThat(encodedList.get(3), is(equalTo(new SimpleEntry<>(2, "a"))));
        assertThat(encodedList.get(4), is(equalTo("d")));
        assertThat(encodedList.get(5), is(equalTo(new SimpleEntry<>(4, "e"))));
    }

    @Test
    public void shouldDecodeEncodedList() throws Exception {
        List<String> encoded = decode(
            Arrays.asList(
                new SimpleEntry<>(4, "a"),
                "b",
                new SimpleEntry<>(2, "c"),
                new SimpleEntry<>(2, "a"),
                "d",
                new SimpleEntry<>(4, "e")));

        assertThat(encoded, hasSize(14));
    }

    @Test
    public void shouldEncodeAList_13() throws Exception {
        List<SimpleEntry<Integer, String>> encodedList = encode_direct(
            Arrays.asList("a", "a", "a", "a", "b", "c", "c", "a", "a", "d", "e", "e", "e", "e"));
        assertThat(encodedList, hasSize(6));
        assertThat(encodedList.get(0), is(equalTo(new SimpleEntry<>(4, "a"))));
        assertThat(encodedList.get(1), is(equalTo(new SimpleEntry<>(1, "b"))));
        assertThat(encodedList.get(2), is(equalTo(new SimpleEntry<>(2, "c"))));
        assertThat(encodedList.get(3), is(equalTo(new SimpleEntry<>(2, "a"))));
        assertThat(encodedList.get(4), is(equalTo(new SimpleEntry<>(1, "d"))));
        assertThat(encodedList.get(5), is(equalTo(new SimpleEntry<>(4, "e"))));
    }

    @Test
    public void shouldDuplicateElementsInAList() throws Exception {
        List<String> duplicates = duplicate(Arrays.asList("a", "b", "c", "d"));
        assertThat(duplicates, hasSize(8));
        assertThat(duplicates, hasItems("a", "a", "b", "b", "c", "c", "d", "d"));
    }

    @Test
    public void shouldDuplicateElementsInAList_15() throws Exception {
        List<String> duplicates = duplicate_15(Arrays.asList("a", "b", "c"), 3);
        assertThat(duplicates, hasSize(9));
        assertThat(duplicates, hasItems("a", "a", "a", "b", "b", "b", "c", "c", "c"));
    }

    @Test
    public void shouldDropEveryNthElement() throws Exception {
        List<String> result = dropEveryNth(
            Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"), 3);
        assertThat(result, hasSize(8));
        assertThat(result, hasItems("a", "b", "d", "e", "g", "h", "j", "k"));
    }

    @Test
    public void shouldReturnSameListWhenNIsLessThanListSize() throws Exception {
        List<String> result = dropEveryNth(Arrays.asList("a", "b"), 3);
        assertThat(result, hasSize(2));
        assertThat(result, hasItems("a", "b"));
    }

    @Test
    public void shouldReturnSameListWhenNIsZero() throws Exception {
        List<String> result = dropEveryNth(
            Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"), 0);
        assertThat(result, hasSize(11));
        assertThat(result, hasItems("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"));
    }

    @Test
    public void shouldSplitInTwoHalves() throws Exception {
        Map<Boolean, List<String>> result = split(
            Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "k"), 3);
        System.out.println(result);
        assertThat(result.get(true), hasItems("a", "b", "c"));
        assertThat(result.get(false), hasItems("d", "e", "f", "g", "h", "i", "k"));
    }

    @Test
    public void shouldGiveSliceOfAList() throws Exception {
        List<String> slice = slice(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "k"),
            3, 7);
        assertThat(slice, hasSize(5));
        assertThat(slice, hasItems("c", "d", "e", "f", "g"));
    }

    @Test
    public void shouldRotateAListByThreeElementsWhenNIs3() throws Exception {
        List<String> rotated = rotate(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h"), 3);
        assertThat(rotated, equalTo(Arrays.asList("d", "e", "f", "g", "h", "a", "b", "c")));
    }

    @Test
    public void shouldReturnSameListWhenNIs0() throws Exception {
        List<String> rotated = rotate(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h"), 0);
        assertThat(rotated, equalTo(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h")));
    }

    @Test
    public void shouldRotateWhenNIsNegative() throws Exception {
        List<String> rotated = rotate(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h"), -2);
        assertThat(rotated, equalTo(Arrays.asList("g", "h", "a", "b", "c", "d", "e", "f")));
    }

    @Test
    public void shouldRemoveKthElementFromList() throws Exception {
        Object[] result = removeAt(Arrays.asList("a", "b", "c", "d"), 2);
        assertThat(result[0], equalTo(Arrays.asList("a", "c", "d")));
        assertThat(result[1], equalTo("b"));
    }

    @Test
    public void shouldInsertElementAtSecondPosition() throws Exception {
        List<String> input = Stream.of("a", "b", "c", "d").collect(toList());
        List<String> result = insertAt(input, 2, "alfa");
        System.out.println(result);
        assertThat(result, hasSize(5));
        assertThat(result, hasItems("a", "alfa", "b", "c", "d"));

    }

    @Test
    public void shouldInsertElementAtFirstPosition() throws Exception {
        List<String> input = Stream.of("a", "b", "c", "d").collect(toList());
        List<String> result = insertAt(input, 1, "alfa");
        assertThat(result, hasSize(5));
        assertThat(result, hasItems("alfa", "a", "b", "c", "d"));

    }

    @Test
    public void shouldInsertElementAtEnd() throws Exception {
        List<String> input = Stream.of("a", "b", "c", "d").collect(toList());
        List<String> result = insertAt(input, 5, "alfa");
        assertThat(result, hasSize(5));
        assertThat(result, hasItems("a", "b", "c", "d", "alfa"));
    }

    @Test
    public void shouldCreateARangeBetween4And9() throws Exception {
        List<Integer> range = range(4, 9);
        assertThat(range, hasSize(6));
        assertThat(range, hasItems(4, 5, 6, 7, 8, 9));

    }

    @Test
    public void shouldReturnAListOfThreeRandomSelectedElements() throws Exception {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            List<String> result = randomSelect(
                Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h"),
                3);
            System.out.println(result);
            assertThat(result, hasSize(3));
        });
    }

    @Test
    public void shouldGive6RandomNumbersFromARangeStartingFrom1To49() throws Exception {
        List<Integer> randomList = randomSelect(6, 1, 49);
        assertThat(randomList, hasSize(6));
        System.out.println(randomList); // One possible output [47, 30, 36, 38, 11, 1]
    }

    @Test
    public void shouldGenerateRandomPermutationOfElementsOfAList() throws Exception {
        List<String> permutation = randomPermutation(
            Stream.of("a", "b", "c", "d", "e", "f").collect(toList()));
        assertThat(permutation, hasSize(6));
        assertThat(permutation, containsInAnyOrder("a", "b", "c", "d", "e", "f"));
        System.out.println(permutation); // one possible output [a, e, f, c, b, d]
    }

    @Test
    public void shouldFindAllCombinationsOfSize3FromAListWithSize6() throws Exception {
        List<String> input = Stream.of("a", "b", "c", "d", "e", "f").collect(toList());
        List<List<String>> combinations = combinations(input, 3);
        assertThat(combinations, hasSize(20));
    }

    @Test
    public void shouldGroupIntoThreeGroupsOfSize_2_3_and_4() throws Exception {
        List<String> input = Stream
            .of("aldo", "beat", "carla", "david", "evi", "flip", "gary", "hugo", "ida")
            .collect(toList());
        List<List<List<String>>> groups = group3(input);
        assertThat(groups, hasSize(1260));
    }

    @Test
    public void shouldSortByElementLength() throws Exception {
        List<List<String>> input = Arrays
            .asList(Arrays.asList("a", "b", "c"), Arrays.asList("d", "e"),
                Arrays.asList("f", "g", "h"), Arrays.asList("d", "e"), Arrays.asList("i", "j", "k"),
                Arrays.asList("m", "n"), Arrays.asList("o"));
        List<List<String>> result = lsort(input);
        assertThat(result, is(equalTo(Arrays
            .asList(Arrays.asList("o"), Arrays.asList("d", "e"), Arrays.asList("d", "e"),
                Arrays.asList("m", "n"), Arrays.asList("a", "b", "c"), Arrays.asList("f", "g", "h"),
                Arrays.asList("i", "j", "k")))));
    }

    // 28
    private <T> List<List<T>> lsort(List<List<T>> input) {
        return input.stream().sorted((List<T> t1, List<T> t2) -> {
            if (t1.size() != t2.size()){
                return t1.size() - t2.size();
            }else {
                for (int key = 0; key < t1.size(); key++) {
                    if (t1.get(key).hashCode() == t2.get(key).hashCode()){
                        continue;
                    }
                    return t1.get(key).hashCode() - t2.get(key).hashCode();
                }
                return 0;
            }
        }).collect(toList());
    }

    // 27
    private <T> List<List<List<T>>> group3(List<T> input) {
        return null;    //难
    }

    // 26
    private <T> List<List<T>> combinations(List<T> input, int n) {
        return null; //难，不会
    }

    // 25
    private <T> List<T> randomPermutation(List<T> collect) {
        return randomSelect(collect, collect.size());
    }

    // 24
    private List<Integer> randomSelect(int i, int j, int k) {
        return randomSelect(IntStream.rangeClosed(j, k).boxed().collect(toList()), i);
    }

    // 23
    private <T> List<T> randomSelect(List<T> list, int n) {
        Random random = new Random();
        List<T> result = new ArrayList<>(list);
        return IntStream.range(0, n).mapToObj(i -> result.remove(random.nextInt(result.size())))
            .collect(toList());
    }

    // 22
    private List<Integer> range(int start, int end) {
        return IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

    // 21
    private <T> List<T> insertAt(List<T> input, int i, T element) {
        Objects.requireNonNull(input, "input required not null.");
        return IntStream.rangeClosed(1, input.size() + 1).mapToObj(k -> {
            if (k < i) {
                return input.get(k - 1);
            }
            if (k == i) {
                return element;
            }
            return input.get(k - 2);
        }).collect(toList());
    }

    // 20
    private <T> Object[] removeAt(List<T> list, int i) {
        List<T> result = new ArrayList<>(list);
        T e = result.remove(i - 1);
        return new Object[]{result, e};
    }

    // 19
    private <T> List<T> rotate(List<T> list, int n) {
        List<T> result = new ArrayList<>();
        if (n == 0) {
            result = list;
        } else if (n < 0) {
            result.addAll(list.subList(list.size() + n, list.size()));
            result.addAll(list.subList(0, list.size() + n));
        } else {
            result.addAll(list.subList(n, list.size()));
            result.addAll(list.subList(0, n));
        }
        return result;
    }

    // 18
    private <T> List<T> slice(List<T> list, int i, int j) {
        return list.subList(i - 1, j);
    }

    // 17
    private <T> Map<Boolean, List<T>> split(List<T> list, int i) {
        return new HashMap<Boolean, List<T>>() {{
            put(Boolean.TRUE, list.subList(0, i));
            put(Boolean.FALSE, list.subList(i, list.size()));
        }};
    }

    private <T> Map<Boolean, List<T>> split1(List<T> list, int i) {
        return IntStream.range(0, list.size())
            .mapToObj(value -> new SimpleEntry<>(value, list.get(value)))
            .collect(partitioningBy(entry -> entry.getKey() < i,
                mapping(SimpleEntry::getValue, toList())));
    }

    // 16
    private <T> List<T> dropEveryNth(List<T> list, int i) {
        if (i == 0) {
            return Collections.unmodifiableList(list);
        }
        List<T> result = new ArrayList<>(list.size() - list.size() / i);
        int flag = 0;
        for (T e : list) {
            if (++flag < i) {
                result.add(e);
            } else {
                flag = 0;
            }
        }
        return result;
    }

    // 15
    private <T> List<T> duplicate_15(List<T> list, int i) {
        return list.stream().map(t -> Stream.generate(() -> t).limit(i)).flatMap(tStream -> tStream)
            .collect(toList());
    }

    // 14
    private <T> List<T> duplicate(List<T> list) {
        return list.stream().map(t -> Stream.of(t, t)).flatMap(tStream -> tStream).collect(
            Collectors.toList());
    }

    // 13
    private List<SimpleEntry<Integer, String>> encode_direct(List<String> list) {
        return null;
    }

    // 12
    private <T> List<T> decode(List<Serializable> list) {
        return list.stream().map(serializable -> {
            if (serializable instanceof SimpleEntry) {
                SimpleEntry<Integer, T> entry = (SimpleEntry<Integer, T>) serializable;
                return Stream.generate(entry::getValue).limit(entry.getKey());
            }
            return Stream.of((T) serializable);
        }).flatMap(t -> t).collect(toList());
    }

    // 11
    private <T> List<Object> encode_modified(List<T> list) {
        Objects.requireNonNull(list, "list required");
        List<Object> result = new ArrayList<>();
        int num = 0;
        T lastElement = null;
        for (T e : list) {
            if (!Objects.equals(e, lastElement) && lastElement != null) {
                if (num == 1) {
                    result.add(lastElement);
                } else {
                    result.add(new SimpleEntry<>(num, lastElement));
                }
                num = 1;
            } else {
                num++;
            }
            lastElement = e;
        }
        if (num == 1) {
            result.add(lastElement);
        } else {
            result.add(new SimpleEntry<>(num, lastElement));
        }
        return result;
    }

    // 10
    private List<SimpleEntry<Integer, String>> encode(List<String> list) {
        Objects.requireNonNull(list, "list required not null!");
        return list.stream()
            .reduce(new ArrayList<SimpleEntry<Integer, String>>(), (result, s) -> {
                SimpleEntry<Integer, String> entry;
                if (result.isEmpty() || !result.get(result.size() - 1).getValue().equals(s)) {
                    entry = new SimpleEntry<>(1, s);
                    result.add(entry);
                } else {
                    entry = new SimpleEntry<>(result.get(result.size() - 1).getKey() + 1, s);
                    result.set(result.size() - 1, entry);
                }
                return result;
            }, (left, right) -> {
                ArrayList<SimpleEntry<Integer, String>> newLeft = new ArrayList<>(left);
                newLeft.addAll(right);
                return newLeft;
            });
    }

    private <T> List<SimpleEntry<Integer, T>> encode1(List<T> list) {
        List<SimpleEntry<Integer, T>> result = new ArrayList<>();
        int num = 0;
        T lastElement = null;
        for (T e : list) {
            if (!Objects.equals(e, lastElement) && lastElement != null) {
                result.add(new SimpleEntry<>(num, lastElement));
                num = 1;
            } else {
                num++;
            }
            lastElement = e;
        }
        result.add(new SimpleEntry<>(num, lastElement));
        return result;
    }

    // 9
    private List<List<String>> pack(List<String> list) {
        Objects.requireNonNull(list, "list required not null!");
        return list.stream().reduce(new ArrayList<List<String>>(), (result, s) -> {
            if (result.isEmpty() || !result.get(result.size() - 1).get(0).equals(s)) {
                List sub = new ArrayList();
                sub.add(s);
                result.add(sub);
            } else {
                result.get(result.size() - 1).add(s);
            }
            return result;
        }, (left, right) -> {
            ArrayList newLeft = new ArrayList(left);
            newLeft.addAll(right);
            return newLeft;
        });
    }

    private <T> List<List<T>> pack1(List<T> list) {
        Objects.requireNonNull(list, "list required not null!");
        List<List<T>> result = new ArrayList<>();
        T lastElement = null;
        List<T> sub = null;
        for (T e : list) {
            if (!Objects.equals(e, lastElement)) {
                sub = new ArrayList<>();
                result.add(sub);
            }
            sub.add(e);
            lastElement = e;
        }
        return result;
    }

    // 8
    private List<String> compress(List<String> list) {
        Objects.requireNonNull(list, "list required not null!");
        return list.stream().reduce(new ArrayList<String>(), (result, s) -> {
            if (result.isEmpty() || !result.get(result.size() - 1).equals(s)) {
                result.add(s);
            }
            return result;
        }, (left, right) -> {
            ArrayList newLeft = new ArrayList(left);
            newLeft.addAll(right);
            return newLeft;
        });
    }

    private <T> List<T> compress1(List<T> list) {
        Objects.requireNonNull(list, "list required not null!");
        List<T> result = new ArrayList<>();
        T lastElement = null;
        for (T e : list) {
            if (!Objects.equals(e, lastElement)) {
                result.add(e);
            }
            lastElement = e;
        }
        return result;
    }

    // 7
    private <T> List<T> flatten(List<?> objs, Class<T> clazz) {
        return objs.stream()
            .flatMap(o -> o instanceof List ? flatten((List<?>) o, clazz).stream() : Stream.of(o))
//            .flatMap(o -> o instanceof List ? ((List<T>)o).stream() : Stream.of(o))
            .map(o -> (T) o).collect(toList());
    }

    // 6
    private boolean isPalindrome(List list) {
        if (list == null) {
            throw new IllegalArgumentException("list can't be null");
        }
        int size = list.size();
        int mid = list.size() / 2;
        return IntStream.rangeClosed(1, mid)
            .anyMatch(i -> Objects.equals(list.get(i - 1), list.get(size - i)));
    }

    // 5
    private List<Integer> reverse(List<Integer> numbers) {
        if (numbers == null) {
            throw new IllegalArgumentException("list can't be null");
        }
        Collections.reverse(numbers);
        return numbers;
    }

    // 4
    private int length(List<Integer> integers) {
        return integers.size();
    }

    // 3
    private <T> T kth(List<Integer> numbers, int i) {
        return (T) numbers.get(i);
    }

    // 2
    private <T> T secondLast(List<Integer> numbers) {
        assertNotNull(numbers);
        if (numbers.size() <= 1) {
            throw new NoSuchElementException();
        }
        return (T) numbers.get(numbers.size() - 2);
    }

    // 1
    private String last(List<String> list) {
        return list.get(list.size() - 1);
    }
}
