package org.terasoluna.gfw.examples.rest.api.common.error;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.WebRequest;

/**
 * Creator of error object for RESTful Web Service.
 */
@Component
public class ApiErrorCreator extends ApplicationObjectSupport {

    /**
     * Create error object for rest.
     * 
     * @param code error code
     * @param locale requested locale.
     * @param arguments arguments for to resolve the message.
     * @return error object
     */
    public ApiError createRestError(WebRequest request, String code, String defaultMessage,
            Object... arguments) {
        String localizedMessage = getMessageSourceAccessor().getMessage(code, arguments,
                defaultMessage, request.getLocale());
        return new ApiError(code, localizedMessage);
    }

    /**
     * Create binding error object for rest.
     * 
     * @param bindingResult result of binding.
     * @param locale requested locale.
     * @return error object
     */
    public ApiError createBindingResultRestError(WebRequest request, String errorCode,
            String defaultMessage, BindingResult bindingResult) {
        ApiError restError = createRestError(request, errorCode, defaultMessage);
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            restError.addDetail(createRestError(request, fieldError, fieldError.getField(),
                    fieldError.getRejectedValue()));
        }
        for (ObjectError objectError : bindingResult.getGlobalErrors()) {
            restError.addDetail(createRestError(request, objectError, objectError.getObjectName()));
        }
        return restError;
    }

    /**
     * Create error detail object for rest.
     * 
     * @param messageResolvable resolvable object for message.
     * @param locale requested locale.
     * @param additionalArguments additional arguments for to resolve the message.
     * @return error detail object
     */
    private ApiError createRestError(WebRequest request,
            DefaultMessageSourceResolvable messageResolvable, String target,
            Object... additionalArguments) {

        // add additional arguments to the message binding arguments.
        List<Object> argumentList = new ArrayList<>();
        argumentList.addAll(Arrays.asList(messageResolvable.getArguments()));
        argumentList.addAll(Arrays.asList(additionalArguments));
        Object[] arguments = argumentList.toArray(new Object[argumentList.size()]);

        // resolve the message.
        String localizedMessage = getMessageSourceAccessor().getMessage(
                new DefaultMessageSourceResolvable(messageResolvable.getCodes(), arguments,
                        messageResolvable.getDefaultMessage()), request.getLocale());

        return new ApiError(messageResolvable.getCode(), localizedMessage, target);
    }

}
