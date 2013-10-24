package org.terasoluna.gfw.examples.ajax.app;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("ajax")
@Controller
public class AjaxController {

    @RequestMapping(value = "simple", method = RequestMethod.GET, params = "form")
    public String ajaxForm() {
        return "ajax/simple";
    }

    @RequestMapping(value = "simple", method = RequestMethod.POST, params = "test")
    @ResponseBody
    public HttpEntity<SimpleResource> ajaxTest(@RequestBody SimpleResource request) {
        request.setA(request.getA() + "_res");
        request.setC(request.getC() + "_res");
        return new HttpEntity<SimpleResource>(request);
    }

}
