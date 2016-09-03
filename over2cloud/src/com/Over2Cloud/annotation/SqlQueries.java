package com.Over2Cloud.annotation;
/**
 * 
 * @author Lalit Nandan Varshney
 * 
 * */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SqlQueries {

	SqlQuery[] value();
}
