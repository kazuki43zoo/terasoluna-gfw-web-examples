package org.terasoluna.gfw.examples.rest.app.common;

import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

/**
 * Creator of error object for REST.
 */
@Component
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

    /**
     * Create error object for rest.
     * 
     * @param code
     *            error code
     * @param locale
     *            requested locale.
     * @return error object
     */
    public RestError createRestError(final String code, final Locale locale) {
        String localizedMessage = null;
        try {
            localizedMessage = messageSource.getMessage(code, null, locale);
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
     * @return error detail object
     */
    public RestErrorDetail createRestErrorDetail(
            final MessageSourceResolvable messageResolvable, final Locale locale) {
        final String code = (messageResolvable.getCodes().length > 0 ? messageResolvable
                .getCodes()[0] : null);
        String localizedMessage = null;
        try {
            localizedMessage = messageSource.getMessage(messageResolvable,
                    locale);
        } catch (NoSuchMessageException e) {
            logger.error("Message not found.", e);
        }
        return new RestErrorDetail(code, localizedMessage);
    }

}
