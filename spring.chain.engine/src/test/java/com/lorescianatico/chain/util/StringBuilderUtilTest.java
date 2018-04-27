package com.lorescianatico.chain.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringBuilderUtilTest {

    @Test
    public void build() {

        String string = StringBuilderUtil.build("A", "B", "C");
        assertEquals("ABC", string);

    }
}