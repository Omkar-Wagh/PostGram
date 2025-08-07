package com.demo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Mark this annotation usable on methods only
@Target(ElementType.METHOD)

// Annotation should be available at runtime for AOP to detect
@Retention(RetentionPolicy.RUNTIME)

public @interface RequireProfile {

}
