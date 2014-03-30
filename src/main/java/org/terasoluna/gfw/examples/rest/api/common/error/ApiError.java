package org.terasoluna.gfw.examples.rest.api.common.error;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.terasoluna.gfw.common.exception.ExceptionCodeProvider;
import org.terasoluna.gfw.examples.rest.api.common.resource.hateoas.AbstractLinksSupportedResource;

public class ApiError extends AbstractLinksSupportedResource implements Serializable,
        ExceptionCodeProvider {

    private static final long serialVersionUID = 1L;

    private final String code;

    private final String message;

    @JsonSerialize(include = Inclusion.NON_EMPTY)
    private final String target;

    @JsonSerialize(include = Inclusion.NON_EMPTY)
    private final List<ApiError> details = new ArrayList<>();

    public ApiError(String code, String message) {
        this(code, message, null);
    }

    public ApiError(String code, String message, String target) {
        this.code = code;
        this.message = message;
        this.target = target;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getTarget() {
        return target;
    }

    public List<ApiError> getDetails() {
        return details;
    }

    public void setDetails(List<ApiError> details) {
        this.details.clear();
        this.details.addAll(details);
    }

    public void addDetail(ApiError detail) {
        details.add(detail);
    }

}
