package com.lorescianatico.chain.configuration;

import com.lorescianatico.chain.executable.Handler;
import com.lorescianatico.chain.fault.UndefinedHandlerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@ConfigurationPropertiesBinding
@Slf4j
public class ChainDefinitionConverter implements Converter<String, Handler> {

    private final Map<String, Handler> handlerMap = new HashMap<>();

    public ChainDefinitionConverter(List<Handler> handlers){
        handlers.forEach(handler -> handlerMap.put(handler.getClass().getName(), handler));
    }

    @Override
    public Handler<?> convert(String source) {
        return Optional.ofNullable(handlerMap.get(source))
                .orElseThrow(() -> {
                    logger.error("Cannot find handler: {}", source);
                    return new UndefinedHandlerException("Undefined handler: " + source);}
                );
    }
}
