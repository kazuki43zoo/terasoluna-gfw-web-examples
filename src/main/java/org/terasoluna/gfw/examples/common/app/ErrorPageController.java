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

    @Value("${app.errorPage.servletNameForApi:apiServlet}")
    String servletNameForApi;

    @Value("${app.errorPage.pathOfErrorPageForApi:/api/error}")
    String pathOfErrorPageForApi;

    @RequestMapping
    public Object handleErrorPage(
            @RequestParam(value = "viewName", required = false) String viewName,
            HttpServletRequest request) {
        Object servletName = request.getAttribute(RequestDispatcher.ERROR_SERVLET_NAME);
        boolean isRestServlet = servletNameForApi.equals(servletName);
        if (isRestServlet) {
            return "forward:" + pathOfErrorPageForApi;
        } else {
            return viewName;
        }
    }

}
