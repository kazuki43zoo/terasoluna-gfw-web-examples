package org.terasoluna.gfw.examples.rest.api.common.error;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

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
    public ApiError createRestError(String code, String defaultMessage, Locale locale,
            Object... arguments) {
        String localizedMessage = getMessageSourceAccessor().getMessage(code, arguments,
                defaultMessage, locale);
        return new ApiError(code, localizedMessage);
    }

    /**
     * Create binding error object for rest.
     * 
     * @param bindingResult result of binding.
     * @param locale requested locale.
     * @return error object
     */
    public ApiError createBindingResultRestError(String errorCode, String defaultMessage,
            BindingResult bindingResult, Locale locale) {
        ApiError restError = createRestError(errorCode, defaultMessage, locale);
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            restError.addDetail(createRestError(fieldError, fieldError.getField(), locale,
                    fieldError.getRejectedValue()));
        }
        for (ObjectError objectError : bindingResult.getGlobalErrors()) {
            restError.addDetail(createRestError(objectError, objectError.getObjectName(), locale));
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
    private ApiError createRestError(DefaultMessageSourceResolvable messageResolvable,
            String target, Locale locale, Object... additionalArguments) {

        // add additional arguments to the message binding arguments.
        List<Object> argumentList = new ArrayList<>();
        argumentList.addAll(Arrays.asList(messageResolvable.getArguments()));
        argumentList.addAll(Arrays.asList(additionalArguments));
        Object[] arguments = argumentList.toArray(new Object[argumentList.size()]);

        // resolve the message.
        String localizedMessage = getMessageSourceAccessor().getMessage(
                new DefaultMessageSourceResolvable(messageResolvable.getCodes(), arguments,
                        messageResolvable.getDefaultMessage()), locale);

        return new ApiError(messageResolvable.getCode(), localizedMessage, target);
    }

}
