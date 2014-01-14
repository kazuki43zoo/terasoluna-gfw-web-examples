package org.terasoluna.gfw.examples.rest.app.common;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("error")
@Controller
public class RestErrorPageController {

    @Inject
    RestErrorCreator restErrorCreator;

    @RequestMapping
    @ResponseBody
    public ResponseEntity<RestError> handleErrorPage(
            final @RequestParam("messageCode") String messageCode,
            final HttpServletRequest request, final Locale locale) {
        final HttpStatus httpStatus = HttpStatus.valueOf((Integer) request
                .getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        final RestError errorBody = restErrorCreator.createRestError(
                messageCode, httpStatus.getReasonPhrase(), locale);
        return new ResponseEntity<RestError>(errorBody, httpStatus);
    }
}
