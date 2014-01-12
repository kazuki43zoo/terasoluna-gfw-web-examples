package org.terasoluna.gfw.examples.rest.app.common;

import java.util.Locale;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.exception.ExceptionCodeResolver;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.exception.SystemException;

/**
 * Global ExceptionHandler for REST.
 */
@ControllerAdvice
public class RestGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Inject
    RestErrorCreator restErrorCreator;

    @Inject
    ExceptionCodeResolver exceptionCodeResolver;

    private String messageCodeOfFieldError;

    private String messageCodeOfObjectError;

    public void setMessageCodeOfFieldError(String messageCodeOfFieldError) {
        this.messageCodeOfFieldError = messageCodeOfFieldError;
    }

    public void setMessageCodeOfObjectError(String messageCodeOfObjectError) {
        this.messageCodeOfObjectError = messageCodeOfObjectError;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final HttpHeaders headers, final HttpStatus status,
            final WebRequest request) {
        final RestError errorBody = createBindingError(ex.getBindingResult(),
                request.getLocale());
        return handleExceptionInternal(ex, errorBody, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(
            final BindException ex, final HttpHeaders headers,
            final HttpStatus status, final WebRequest request) {
        final RestError errorBody = createBindingError(ex.getBindingResult(),
                request.getLocale());
        return handleExceptionInternal(ex, errorBody, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            final HttpMessageNotReadableException ex,
            final HttpHeaders headers, final HttpStatus status,
            final WebRequest request) {
        final String code;
        if (ex.getCause() instanceof Exception) {
            code = exceptionCodeResolver.resolveExceptionCode((Exception) ex
                    .getCause());
        } else {
            code = exceptionCodeResolver.resolveExceptionCode(ex);
        }
        RestError errorBody = restErrorCreator.createRestError(code,
                request.getLocale());
        return handleExceptionInternal(ex, errorBody, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            final Exception ex, final Object body, final HttpHeaders headers,
            final HttpStatus status, final WebRequest request) {
        Object errorBody = body;
        if (errorBody == null) {
            final String code = exceptionCodeResolver.resolveExceptionCode(ex);
            errorBody = restErrorCreator.createRestError(code,
                    request.getLocale());
        }
        return new ResponseEntity<Object>(errorBody, headers, status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(
            final ResourceNotFoundException ex, final WebRequest request) {
        return handleExceptionInternal(ex, null, null, HttpStatus.NOT_FOUND,
                request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(
            final BusinessException ex, final WebRequest request) {
        return handleExceptionInternal(ex, null, null, HttpStatus.CONFLICT,
                request);
    }

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<Object> handleSystemException(
            final SystemException ex, final WebRequest request) {
        RestError errorBody = restErrorCreator.createRestError(ex.getCode(),
                request.getLocale());
        return handleExceptionInternal(ex, errorBody, null,
                HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherException(final Exception ex,
            final WebRequest request) {
        return handleExceptionInternal(ex, null, null,
                HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private RestError createBindingError(final BindingResult bindingResult,
            final Locale locale) {
        final RestError restError;
        if (bindingResult.hasFieldErrors()) {
            restError = restErrorCreator.createRestError(
                    messageCodeOfFieldError, locale);
            for (final FieldError fieldError : bindingResult.getFieldErrors()) {
                restError.addDetail(restErrorCreator.createRestErrorDetail(
                        fieldError, locale));
            }
        } else {
            restError = restErrorCreator.createRestError(
                    messageCodeOfObjectError, locale);
            for (final ObjectError objectError : bindingResult
                    .getGlobalErrors()) {
                restError.addDetail(restErrorCreator.createRestErrorDetail(
                        objectError, locale));
            }
        }
        return restError;
    }

}
