package com.lorescianatico.chain.stereotype;

import org.springframework.core.annotation.AliasFor;
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
    @AliasFor(
            annotation = Component.class
    )
    String value() default "";
}
