package org.terasoluna.gfw.examples.rest.api.common.http;

import static com.google.common.net.HttpHeaders.*;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

/**
 * Utility for HTTP Response of RESTful Web Service.
 */
public class ResponseUtils {

    /**
     * Default constructor.
     */
    private ResponseUtils() {
        throw new IllegalAccessError(getClass() + " cannot insantiate.");
    }

    /**
     * Create response entity for OPTIONS.
     * <p>
     * Append allowed method list to the headers to be written to the response.
     * </p>
     * <p>
     * If not contains "OPTIONS" method in allowed methods, append the "OPTIONS" method in allowed
     * methods.
     * </p>
     * 
     * @param allowedMethods allowed method list.
     * @return response entity for OPTIONS(returned http status code is 200(OK)).
     */
    public static HttpEntity<Void> createHttpEntityOfOptions(HttpMethod... allowedMethods) {

        final Set<HttpMethod> allowedMethodSet = new LinkedHashSet<>(Arrays.asList(allowedMethods));
        if (!allowedMethodSet.contains(HttpMethod.OPTIONS)) {
            allowedMethodSet.add(HttpMethod.OPTIONS);
        }

        final HttpHeaders headers = new HttpHeaders();
        headers.setAllow(allowedMethodSet);

        return new HttpEntity<>(headers);
    }

    public static <T> HttpEntity<T> createHttpEntityOfPost(T body, URI location) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return new HttpEntity<>(body, headers);
    }

    public static void addHeadersForCache(HttpServletResponse response, int cacheSeconds,
            boolean isMustRevalidate) {
        // for HTTP 1.0
        response.setDateHeader(EXPIRES,
                System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(cacheSeconds));
        // for HTTP 1.1
        response.addHeader(CACHE_CONTROL, "private");
        response.addHeader(CACHE_CONTROL, "max-age=" + cacheSeconds);
        if (isMustRevalidate) {
            response.addHeader(CACHE_CONTROL, "must-revalidate");
        }
    }

    public static void addHeadersForNoCache(HttpServletResponse response) {
        // for HTTP 1.0
        response.setDateHeader(EXPIRES, 0L);
        response.setHeader(PRAGMA, "no-cache");
        // for HTTP 1.1
        response.addHeader(CACHE_CONTROL, "no-cache");
        response.addHeader(CACHE_CONTROL, "no-store");
    }

}
