package org.terasoluna.gfw.examples.ajax.app;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("ajax")
@Controller
public class AjaxController {

    @RequestMapping(value = "simple", method = RequestMethod.GET)
    public String ajaxForm() {
        return "ajax/simple";
    }

    @RequestMapping(value = "simple", method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<SimpleResource> ajaxTest(@RequestBody SimpleResource request) {
        request.setA(request.getA() + "_res");
        request.setC(request.getC() + "_res");
        return new HttpEntity<SimpleResource>(request);
    }

    @RequestMapping(value = "xxe", method = RequestMethod.POST)
    @ResponseBody
    public SimpleResource xxe(@RequestBody SimpleResource request) {
        return request;
    }

    @RequestMapping(value = "xxes", method = RequestMethod.POST)
    @ResponseBody
    public List<SimpleResource> xxe(@RequestBody List<SimpleResource> request) {
        return request;
    }

    @RequestMapping(value = "xxe", method = RequestMethod.POST, params = "sax")
    @ResponseBody
    public SimpleResource xxe(@RequestBody SAXSource request) throws IOException {
        byte[] bytes = new byte[1024];
        BufferedInputStream in = new BufferedInputStream(request.getInputSource().getByteStream());
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        while (true) {
            int readedBytes = in.read(bytes);
            if (readedBytes <= 0) {
                break;
            }
            bout.write(bytes, 0, readedBytes);
        }
        in.close();
        bout.close();
        String bodyText = new String(bout.toByteArray());
        SimpleResource resource = new SimpleResource();
        resource.setA(bodyText);
        return resource;
    }

}
