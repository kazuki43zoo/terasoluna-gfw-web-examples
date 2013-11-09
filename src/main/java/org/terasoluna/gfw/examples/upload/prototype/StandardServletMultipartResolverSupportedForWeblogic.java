package org.terasoluna.gfw.examples.upload.prototype;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

public class StandardServletMultipartResolverSupportedForWeblogic extends StandardServletMultipartResolver {

    @Override
    public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {
        return new StandardMultipartHttpServletRequest(request) {
            public Enumeration<String> getParameterNames() {
                Enumeration<String> parameterNames = super.getParameterNames();
                if (parameterNames.hasMoreElements()) {
                    return parameterNames;
                }
                List<String> parameterNameList = new ArrayList<String>();
                try {
                    for (Part part : getParts()) {
                        if (part.getContentType() == null) {
                            parameterNameList.add(part.getName());
                        }
                    }
                } catch (IOException e) {
                    throw new IllegalArgumentException(e);
                } catch (ServletException e) {
                    throw new IllegalArgumentException(e);
                }
                return Collections.enumeration(parameterNameList);
            }

        };
    }
}
