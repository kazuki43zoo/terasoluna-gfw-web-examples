package org.terasoluna.gfw.examples.rest.app.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

public class RestError implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String code;
    private final String message;
    @JsonSerialize(include = Inclusion.NON_EMPTY)
    private final List<RestError> details = new ArrayList<>();

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

    public List<RestError> getDetails() {
        return details;
    }

    public void setDetails(List<RestError> details) {
        this.details.clear();
        this.details.addAll(details);
    }

    public void addDetail(RestError detail) {
        details.add(detail);
    }

}
