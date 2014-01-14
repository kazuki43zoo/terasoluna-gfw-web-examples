package org.terasoluna.gfw.examples.common.app;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("error")
@Controller
public class ErrorPageController {

    @Value("${app.errorPage.servletNameForRest:restAppServlet}")
    String servletNameForRest;

    @Value("${app.errorPage.pathOfErrorHandlingForRest:/rest/error}")
    String pathOfErrorPageForRest;

    @RequestMapping
    public Object handleErrorPage(
            final @RequestParam(value = "viewName", required = false) String viewName,
            final HttpServletRequest request) {
        final Object servletName = request
                .getAttribute(RequestDispatcher.ERROR_SERVLET_NAME);
        final boolean isRestServlet = servletNameForRest.equals(servletName);
        if (isRestServlet) {
            return "forward:" + pathOfErrorPageForRest;
        } else {
            return viewName;
        }
    }

}
