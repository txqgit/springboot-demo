package com.example.common.advice.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
@RestController
@RequestMapping
public @interface ComRestController {

    @AliasFor(annotation = RequestMapping.class) String[] value() default {};

    @AliasFor(annotation = RequestMapping.class) String[] path() default {};

    @AliasFor(annotation = RequestMapping.class) String[] produces() default {"application/json;charset=UTF-8"};

    @AliasFor(annotation = RequestMapping.class) String[] consumes() default {};
}
