package com.study.java.lambda.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jeffrey
 * @since 2017/03/15 14:23
 */
public class OptionalDemo {

    @Test
    public void test() {
        Optional<String> a = Optional.of("a");
        System.out.println(a.flatMap(s -> Optional.of(s)).get());
        Assert.assertEquals("a", a.get());
        Assert.assertTrue(a.isPresent());

        // null
        Optional empty1 = Optional.empty();
        Optional empty2 = Optional.ofNullable(null);
        Assert.assertEquals(true, empty1.equals(empty2));
        Assert.assertFalse(empty1.isPresent());
        Assert.assertFalse(empty2.isPresent());

        Assert.assertEquals("A", empty1.orElse("A"));
        Assert.assertEquals("B", empty2.orElseGet(() -> "B"));
    }

    @Test
    public void test1() {
        Arrays.asList(1, 2, 3, 4, 5).stream().max(Integer::compareTo)
            .ifPresent(System.out::println);
        new ArrayList<Integer>().stream().max(Integer::compareTo).ifPresent(System.out::println);
        String result = Optional.empty().map(o -> o.toString()).orElse("test");
        Assert.assertEquals("test", result);
    }

    @Test
    public void test2() {
        Arrays.asList(1, 2, 3, 4, 5).stream().reduce((value1, value2) -> value1 + value2)
            .ifPresent(System.out::println);
        Assert.assertEquals(Integer.valueOf(25),
            Arrays.asList(1, 2, 3, 4, 5).stream().reduce(10, (value1, value2) -> value1 + value2));
    }

    @Test
    public void test3() {
        List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        System.out.println(values.stream().mapToInt(Integer::intValue).sum());
        System.out.println(values.stream().mapToInt(Integer::intValue).reduce(0, Integer::sum));
        Integer[] integers = values.stream().toArray(Integer[]::new);
        System.out.println(integers.length);
    }

    @Test
    public void test4() {
        Insurance insurance = new Insurance();
        insurance.setName("hah");
        Car car = new Car();
//        car.setInsurance(insurance);
        Person person = new Person();
        person.setCar(car);

        String insuranceName = Optional.ofNullable(person).map(Person::getCar)
            .map(Car::getInsurance).map(Insurance::getName).orElseGet(() -> String.valueOf("null"));
        System.out.println(insuranceName);
    }

    public Optional<Insurance> nullSafeFindCheapestInsurance(Optional<Person> person,
        Optional<Car> car) {
        return person.flatMap(p -> car.map(c -> findCheapestInsurance(p, c)));
    }

    public Insurance findCheapestInsurance(Person person, Car car) {
        Objects.requireNonNull(person);
        Objects.requireNonNull(car);
        Insurance insurance = new Insurance();
        insurance.setName("cheapest");
        return insurance;
    }

    private static class Person {

        private Car car;

        public Car getCar() {
            return car;
        }

        public Optional<Car> getCarAsOptional() {
            return Optional.ofNullable(car);
        }

        public void setCar(Car car) {
            this.car = car;
        }
    }

    private static class Car {

        private Insurance insurance;

        public Insurance getInsurance() {
            return insurance;
        }

        public void setInsurance(Insurance insurance) {
            this.insurance = insurance;
        }
    }

    private static class Insurance {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
