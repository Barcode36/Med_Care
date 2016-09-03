package com.Over2Cloud.procegure.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.Over2Cloud.procegure.annotation.SqlQuery1;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SqlQueries {
	SqlQuery1[] value();
}
