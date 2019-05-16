package com.yicj.study.common;

import java.lang.annotation.*;

@Documented
@Inherited
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Init {
    String value() default "";
}