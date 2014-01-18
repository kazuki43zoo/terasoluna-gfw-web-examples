package org.terasoluna.gfw.examples.rest.app.common;

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
 * Creator of error object for REST.
 */
@Component
public class RestErrorCreator extends ApplicationObjectSupport {

    /**
     * Create error object for rest.
     * 
     * @param code
     *            error code
     * @param locale
     *            requested locale.
     * @param arguments
     *            arguments for to resolve the message.
     * @return error object
     */
    public RestError createRestError(final String code,
            final String defaultMessage, final Locale locale,
            final Object... arguments) {
        String localizedMessage = getMessageSourceAccessor().getMessage(code,
                arguments, defaultMessage, locale);
        return new RestError(code, localizedMessage);
    }

    /**
     * Create binding error object for rest.
     * 
     * @param bindingResult
     *            result of binding.
     * @param locale
     *            requested locale.
     * @return error object
     */
    public RestError createBindingResultRestError(final String errorCode,
            final BindingResult bindingResult, final String defaultMessage,
            final Locale locale) {
        RestError restError = createRestError(errorCode, defaultMessage, locale);
        for (final FieldError fieldError : bindingResult.getFieldErrors()) {
            restError.addDetail(createRestError(fieldError, locale,
                    fieldError.getRejectedValue()));
        }
        for (final ObjectError objectError : bindingResult.getGlobalErrors()) {
            restError.addDetail(createRestError(objectError, locale));
        }
        return restError;
    }

    /**
     * Create error detail object for rest.
     * 
     * @param messageResolvable
     *            resolvable object for message.
     * @param locale
     *            requested locale.
     * @param additionalArguments
     *            additional arguments for to resolve the message.
     * @return error detail object
     */
    private RestError createRestError(
            final DefaultMessageSourceResolvable messageResolvable,
            final Locale locale, final Object... additionalArguments) {

        // add additional arguments to the message binding arguments.
        final List<Object> argumentList = new ArrayList<>();
        argumentList.addAll(Arrays.asList(messageResolvable.getArguments()));
        argumentList.addAll(Arrays.asList(additionalArguments));
        final Object[] arguments = argumentList.toArray(new Object[argumentList
                .size()]);

        // resolve the message.
        final String localizedMessage = getMessageSourceAccessor().getMessage(
                new DefaultMessageSourceResolvable(
                        messageResolvable.getCodes(), arguments,
                        messageResolvable.getDefaultMessage()), locale);

        return new RestError(messageResolvable.getCode(), localizedMessage);
    }

}
