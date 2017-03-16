package com.study.java.lambda.function;

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
}
