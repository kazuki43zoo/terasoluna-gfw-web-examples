package org.terasoluna.gfw.examples.rest.app.common;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Utility for HTTP Response of RESTful Web Service.
 */
public class RestResponseUtils {

    /**
     * Default constructor.
     */
    private RestResponseUtils() {
        throw new IllegalAccessError(getClass() + " cannot insantiate.");
    }

    /**
     * Create response entity for OPTIONS.
     * <p>
     * Append allowed method list to the headers to be written to the response.
     * </p>
     * <p>
     * If not contains "OPTIONS" method in allowed methods, append the "OPTIONS"
     * method in allowed methods.
     * </p>
     * 
     * @param allowedMethods
     *            allowed method list.
     * @return response entity for OPTIONS(returned http status code is
     *         200(OK)).
     */
    public static ResponseEntity<Void> createEntityOfOptions(
            final HttpMethod... allowedMethods) {

        final Set<HttpMethod> allowedMethodSet = new LinkedHashSet<>(
                Arrays.asList(allowedMethods));
        if (!allowedMethodSet.contains(HttpMethod.OPTIONS)) {
            allowedMethodSet.add(HttpMethod.OPTIONS);
        }

        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setAllow(allowedMethodSet);

        return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
    }

}
