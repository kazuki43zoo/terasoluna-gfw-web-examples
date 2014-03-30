package org.terasoluna.gfw.examples.rest.api.common.error;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.exception.ExceptionCodeProvider;
import org.terasoluna.gfw.common.exception.ExceptionCodeResolver;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessage;

/**
 * Global ExceptionHandler for RESTful Web Service.
 */
@ControllerAdvice
public class ApiGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Inject
    ApiErrorCreator apiErrorCreator;

    @Inject
    ExceptionCodeResolver exceptionCodeResolver;

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        Object apiError;
        String code = null;
        if (body == null) {
            code = exceptionCodeResolver.resolveExceptionCode(ex);
            apiError = apiErrorCreator.createRestError(code, ex.getLocalizedMessage(),
                    request.getLocale());
        } else {
            apiError = body;
            if (apiError instanceof ExceptionCodeProvider) {
                code = ((ExceptionCodeProvider) apiError).getCode();
            }
        }
        if (code != null) {
            if (headers == null) {
                headers = new HttpHeaders();
            }
            ApiErrorHttpHeaders.EXCEPTION_CODE.set(headers, code);
        }
        return new ResponseEntity<>(apiError, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        return handleBindingResult(ex, ex.getBindingResult(), ex.getLocalizedMessage(), headers,
                status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        return handleBindingResult(ex, ex.getBindingResult(), ex.getLocalizedMessage(), headers,
                status, request);
    }

    protected ResponseEntity<Object> handleBindingResult(Exception ex, BindingResult bindingResult,
            String defaultMessage, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String code = exceptionCodeResolver.resolveExceptionCode(ex);
        ApiError apiError = apiErrorCreator.createBindingResultRestError(code,
                ex.getLocalizedMessage(), bindingResult, request.getLocale());
        return handleExceptionInternal(ex, apiError, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        if (ex.getCause() instanceof Exception) {
            return handleExceptionInternal((Exception) ex.getCause(), null, headers, status,
                    request);
        } else {
            return handleExceptionInternal(ex, null, headers, status, request);
        }
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex,
            WebRequest request) {
        return handleExceptionInternal(ex, null, null, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        ApiError apiError;
        if (ex.getResultMessages().getList().size() == 1) {
            ResultMessage resultMessage = ex.getResultMessages().getList().iterator().next();
            apiError = apiErrorCreator.createRestError(resultMessage.getCode(),
                    resultMessage.getText(), request.getLocale(), resultMessage.getArgs());
        } else {
            String code = exceptionCodeResolver.resolveExceptionCode(ex);
            apiError = apiErrorCreator.createRestError(code, ex.getLocalizedMessage(),
                    request.getLocale());
            for (ResultMessage resultMessage : ex.getResultMessages().getList()) {
                apiError.addDetail(apiErrorCreator.createRestError(resultMessage.getCode(),
                        resultMessage.getText(), request.getLocale(), resultMessage.getArgs()));
            }
        }
        return handleExceptionInternal(ex, apiError, null, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, null, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
