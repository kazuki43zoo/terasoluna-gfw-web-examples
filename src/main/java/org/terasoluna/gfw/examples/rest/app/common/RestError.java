package org.terasoluna.gfw.examples.rest.app.common;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

public class RestError {

    private final String code;
    private final String message;
    @JsonSerialize(include=Inclusion.NON_EMPTY)
    private final List<RestErrorDetail> details = new ArrayList<>();

    public RestError(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<RestErrorDetail> getDetails() {
        return details;
    }

    public void addDetail(RestErrorDetail detail) {
        details.add(detail);
    }

}
