package org.terasoluna.gfw.examples.sequencer.app;

import javax.inject.Inject;

import org.dozer.Mapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.examples.common.Messages;
import org.terasoluna.gfw.examples.common.domain.model.Article;
import org.terasoluna.gfw.examples.common.domain.service.ArticleSharedService;

@RequestMapping("sequencer")
@Controller
public class SequencerController {

    @Inject
    ArticleSharedService articleSharedService;

    @Inject
    Mapper beanMapper;

    @ModelAttribute
    public CreateForm setUpCreateForm() {
        return new CreateForm();
    }

    @RequestMapping(value = "create", method = RequestMethod.GET, params = "form")
    public String createForm() {
        return "sequencer/createForm";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST, params = "confirm")
    public String createConfirm(@Validated CreateForm form, BindingResult result) {
        if (result.hasErrors()) {
            return createRedo(form);
        }
        return "sequencer/createConfirm";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST, params = "redo")
    public String createRedo(CreateForm form) {
        return "sequencer/createForm";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Validated CreateForm form, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return createRedo(form);
        }

        Article inputtedArticle = beanMapper.map(form, Article.class);

        Article createdArticle = articleSharedService.createArticle(inputtedArticle, form.isUsingSequencer());

        redirectAttributes.addFlashAttribute(createdArticle);
        redirectAttributes.addFlashAttribute(ResultMessages.success().add(
                Messages.SE_ARTICLE_CREATED.getResultMessage(createdArticle.getArticleId())));

        return "redirect:/sequencer/create?complete";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET, params = "complete")
    public String createComplete() {
        return "sequencer/createComplete";
    }

}
