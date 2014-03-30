package org.terasoluna.gfw.examples.rest.api.common.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.terasoluna.gfw.examples.rest.api.common.http.ResponseCache.CacheType;

@Component
public class ResponseCacheInterceptor extends HandlerInterceptorAdapter {

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex) throws Exception {

        if (ex != null) {
            ResponseUtils.addHeadersForNoCache(response);
            return;
        }

        if (!(handler instanceof HandlerMethod)) {
            return;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        ResponseCache cacheControl = handlerMethod.getMethodAnnotation(ResponseCache.class);
        if (cacheControl == null) {
            return;
        }

        if (cacheControl.cacheType() == CacheType.CACHE) {
            if (0 <= cacheControl.cacheSeconds()) {
                ResponseUtils.addHeadersForCache(response, cacheControl.cacheSeconds(),
                        cacheControl.isMustRevalidate());
            }
        } else {
            ResponseUtils.addHeadersForNoCache(response);
        }

    }

}
