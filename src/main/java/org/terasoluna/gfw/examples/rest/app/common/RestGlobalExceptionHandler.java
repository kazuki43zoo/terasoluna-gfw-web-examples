package org.terasoluna.gfw.examples.rest.app.common;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final HttpHeaders headers, final HttpStatus status,
            final WebRequest request) {
        final RestError errorBody = restErrorCreator.createBindingError(
                ex.getBindingResult(), ex.getLocalizedMessage(),
                request.getLocale());
        return handleExceptionInternal(ex, errorBody, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(
            final BindException ex, final HttpHeaders headers,
            final HttpStatus status, final WebRequest request) {
        final RestError errorBody = restErrorCreator.createBindingError(ex,
                ex.getLocalizedMessage(), request.getLocale());
        return handleExceptionInternal(ex, errorBody, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            final HttpMessageNotReadableException ex,
            final HttpHeaders headers, final HttpStatus status,
            final WebRequest request) {
        if (ex.getCause() instanceof Exception) {
            return handleExceptionInternal((Exception) ex.getCause(), null,
                    headers, status, request);
        } else {
            return handleExceptionInternal(ex, null, headers, status, request);
        }
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            final Exception ex, final Object body, final HttpHeaders headers,
            final HttpStatus status, final WebRequest request) {
        final Object errorBody;
        if (body == null) {
            final String code = exceptionCodeResolver.resolveExceptionCode(ex);
            errorBody = restErrorCreator.createRestError(code,
                    ex.getLocalizedMessage(), request.getLocale());
        } else {
            errorBody = body;
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
        final RestError errorBody = restErrorCreator.createRestError(
                ex.getCode(), ex.getLocalizedMessage(), request.getLocale());
        return handleExceptionInternal(ex, errorBody, null,
                HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherException(final Exception ex,
            final WebRequest request) {
        return handleExceptionInternal(ex, null, null,
                HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
