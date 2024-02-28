package com.lorescianatico.chain.configuration;

import com.lorescianatico.chain.executable.Handler;
import com.lorescianatico.chain.fault.UndefinedHandlerException;
import com.lorescianatico.chain.stereotype.AnotherDummyHandler;
import com.lorescianatico.chain.stereotype.DummyExceptionHandler;
import com.lorescianatico.chain.stereotype.DummyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChainDefinitionConverterTest {

    private final List<Handler> list = List.of(new DummyHandler(), new AnotherDummyHandler(), new DummyExceptionHandler());

    private ChainDefinitionConverter converter;

    @BeforeEach
    void setup(){
        converter = new ChainDefinitionConverter(list);
    }

    @Test
    void convert() {

        assertNotNull(converter.convert("com.lorescianatico.chain.stereotype.DummyHandler"));
        assertThrows(UndefinedHandlerException.class, () -> converter.convert("random"));

    }
}