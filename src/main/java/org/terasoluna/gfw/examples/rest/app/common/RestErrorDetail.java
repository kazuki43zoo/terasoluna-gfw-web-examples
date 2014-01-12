package org.terasoluna.gfw.examples.rest.app.common;

import java.io.Serializable;

public class RestErrorDetail implements Serializable {

    private static final long serialVersionUID = 1L;

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
