package org.terasoluna.gfw.examples.utilities.app;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("utilities/hiddensTag/test")
@Controller
public class HiddensTagTestController {

    @ModelAttribute
    public RootForm setUpForm() {
        return new RootForm();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String testForm(RootForm rootForm) {
        return "utilities/hiddensTag/testForm";
    }

    @RequestMapping(method = RequestMethod.POST, params = "redo")
    public String testRedo(RootForm rootForm) {
        return "utilities/hiddensTag/testForm";
    }

    @RequestMapping(method = RequestMethod.POST, params = "confirm")
    public String testConfirm(@Validated RootForm rootForm, BindingResult result) {
        if (result.hasErrors()) {
            return testRedo(rootForm);
        }
        return "utilities/hiddensTag/testConfirm";
    }

}
