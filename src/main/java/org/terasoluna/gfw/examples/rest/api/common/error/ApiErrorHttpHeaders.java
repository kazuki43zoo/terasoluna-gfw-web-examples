package org.terasoluna.gfw.examples.rest.api.common.error;

import org.springframework.http.HttpHeaders;

public enum ApiErrorHttpHeaders {
    EXCEPTION_CODE("X-Exception-Code");
    private final String headerName;

    private ApiErrorHttpHeaders(String headerName) {
        this.headerName = headerName;
    }

    public void set(HttpHeaders headers, String value) {
        headers.set(headerName, value);
    }

    public String get(HttpHeaders headers) {
        return headers.getFirst(headerName);
    }

}
