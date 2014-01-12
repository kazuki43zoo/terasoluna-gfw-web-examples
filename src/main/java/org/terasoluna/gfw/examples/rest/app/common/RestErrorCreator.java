package org.terasoluna.gfw.examples.rest.app.common;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * Creator of error object for REST.
 */
public class RestErrorCreator {

    /**
     * logger.
     */
    private static final Logger logger = LoggerFactory
            .getLogger(RestErrorCreator.class);

    /**
     * MessageSource object.
     */
    @Inject
    MessageSource messageSource;

    private final String messageCodeOfFieldError;

    private final String messageCodeOfObjectError;

    @ConstructorProperties({ "messageCodeOfFieldError",
            "messageCodeOfObjectError" })
    public RestErrorCreator(final String messageCodeOfFieldError,
            final String messageCodeOfObjectError) {

        Assert.hasText(messageCodeOfFieldError,
                "messageCodeOfFieldError must not be empty.");
        Assert.hasText(messageCodeOfObjectError,
                "messageCodeOfObjectError must not be empty.");

        this.messageCodeOfFieldError = messageCodeOfFieldError;
        this.messageCodeOfObjectError = messageCodeOfObjectError;
    }

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
    public RestError createRestError(final String code, final Locale locale,
            final Object... arguments) {
        String localizedMessage = null;
        try {
            localizedMessage = messageSource
                    .getMessage(code, arguments, locale);
        } catch (NoSuchMessageException e) {
            logger.error("Message not found.", e);
        }
        return new RestError(code, localizedMessage);
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
    public RestErrorDetail createRestErrorDetail(
            final MessageSourceResolvable messageResolvable,
            final Locale locale, final Object... additionalArguments) {

        // add default message to the code list for to resolve the message.
        final List<String> codeList = new ArrayList<>();
        codeList.add(messageResolvable.getDefaultMessage());
        codeList.addAll(Arrays.asList(messageResolvable.getCodes()));
        final String[] codes = codeList.toArray(new String[codeList.size()]);

        // add additional arguments to the message binding arguments.
        final List<Object> argumentList = new ArrayList<>();
        argumentList.addAll(Arrays.asList(messageResolvable.getArguments()));
        argumentList.addAll(Arrays.asList(additionalArguments));
        final Object[] arguments = argumentList.toArray(new Object[argumentList
                .size()]);

        // resolve the message.
        final String localizedMessage = messageSource.getMessage(
                new DefaultMessageSourceResolvable(codes, arguments,
                        messageResolvable.getDefaultMessage()), locale);

        // search the used code for to resolve the message.
        String usedCode = null;
        for (String code : codes) {
            try {
                messageSource.getMessage(code, null, locale);
                usedCode = code;
                break;
            } catch (NoSuchMessageException e) {
                ;
            }
        }
        if (usedCode == null) {
            usedCode = codeList.get(1);
        }

        return new RestErrorDetail(usedCode, localizedMessage);
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
    public RestError createBindingError(final BindingResult bindingResult,
            final Locale locale) {
        final RestError restError;
        if (bindingResult.hasFieldErrors()) {
            restError = createRestError(messageCodeOfFieldError, locale);
            for (final FieldError fieldError : bindingResult.getFieldErrors()) {
                restError.addDetail(createRestErrorDetail(fieldError, locale,
                        fieldError.getRejectedValue()));
            }
        } else {
            restError = createRestError(messageCodeOfObjectError, locale);
            for (final ObjectError objectError : bindingResult
                    .getGlobalErrors()) {
                restError.addDetail(createRestErrorDetail(objectError, locale));
            }
        }
        return restError;
    }

}
