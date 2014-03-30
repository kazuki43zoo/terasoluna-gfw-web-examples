package org.terasoluna.gfw.examples.rest.api.common.error;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            HttpServletRequest request, Locale locale) {
        HttpStatus httpStatus = HttpStatus.valueOf((Integer) request
                .getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        ApiError apiError = restErrorCreator.createRestError(errorCode,
                httpStatus.getReasonPhrase(), locale);
        HttpHeaders headers = new HttpHeaders();
        ApiErrorHttpHeaders.EXCEPTION_CODE.set(headers, errorCode);
        return new ResponseEntity<>(apiError, headers, httpStatus);
    }
}
