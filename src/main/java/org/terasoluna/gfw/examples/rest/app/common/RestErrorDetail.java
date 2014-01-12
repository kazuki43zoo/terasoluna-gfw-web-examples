package org.terasoluna.gfw.examples.rest.app.common;

public class RestErrorDetail {

    private final String code;
    private final String message;

    public RestErrorDetail(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
