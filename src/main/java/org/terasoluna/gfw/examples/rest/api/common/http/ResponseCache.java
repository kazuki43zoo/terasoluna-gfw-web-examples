package org.terasoluna.gfw.examples.rest.api.common.http;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseCache {

    public enum CacheType {
        CACHE, NO_CACHE
    }

    CacheType cacheType() default CacheType.CACHE;

    int cacheSeconds() default 300;

    boolean isMustRevalidate() default false;

}
