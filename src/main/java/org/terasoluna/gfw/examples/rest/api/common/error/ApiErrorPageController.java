package org.terasoluna.gfw.examples.rest.api.common.error;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.terasoluna.gfw.examples.rest.api.common.http.ResponseCache;
import org.terasoluna.gfw.examples.rest.api.common.http.ResponseCache.CacheType;

@RequestMapping("error")
@Controller
public class ApiErrorPageController {

    @Inject
    ApiErrorCreator restErrorCreator;

    @RequestMapping
    @ResponseCache(cacheType = CacheType.NO_CACHE)
    public ResponseEntity<ApiError> handleErrorPage(@RequestParam("errorCode") String errorCode,
            WebRequest request) {

        HttpStatus httpStatus = HttpStatus.valueOf((Integer) request.getAttribute(
                RequestDispatcher.ERROR_STATUS_CODE, WebRequest.SCOPE_REQUEST));
        ApiError apiError = restErrorCreator.createRestError(request, errorCode,
                httpStatus.getReasonPhrase());
        HttpHeaders headers = new HttpHeaders();
        ApiErrorHttpHeaders.EXCEPTION_CODE.set(headers, errorCode);
        return new ResponseEntity<>(apiError, headers, httpStatus);
    }

}
