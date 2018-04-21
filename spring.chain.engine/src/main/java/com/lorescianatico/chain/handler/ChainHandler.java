package com.lorescianatico.chain.handler;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Chain handler stereotype
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ChainHandler {
}
