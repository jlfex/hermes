package com.jlfex.hermes.common.dict;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典元素注解
 * 
 * @author ultrafrog
 * @version 1.0, 2013-11-05
 * @since 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Element {
	public String value() default "";
}
