package org.terasoluna.gfw.examples.utilities.app;

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
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;

@TransactionTokenCheck("utilities/hiddensTag/create")
@RequestMapping("utilities/hiddensTag/create")
@Controller
public class HiddensTagController {

    // ---
    // dependency components.
    // ---
    @Inject
    ArticleSharedService articleSharedService;

    @Inject
    Mapper beanMapper;

    // ---
    // For use case.
    // ---
    @ModelAttribute
    public TitleForm setUpTitleForm(TitleForm titleForm) {
        return titleForm;
    }

    @ModelAttribute
    public OverviewForm setUpOverviewForm(OverviewForm overviewForm) {
        return overviewForm;
    }

    @ModelAttribute
    public ContentForm setUpContentForm(ContentForm contentForm) {
        return contentForm;
    }

    @ModelAttribute
    public AuthorForm setUpAuthorForm(AuthorForm authorForm) {
        return authorForm;
    }

    @ModelAttribute
    public ConfirmForm setUpConfirmForm() {
        return new ConfirmForm();
    }

    // ---
    // request handlers for title form.
    // ---
    @RequestMapping(method = RequestMethod.GET)
    public String createForm() {
        return "utilities/hiddensTag/titleForm";
    }

    @RequestMapping(method = RequestMethod.POST, params = "moveOverview")
    public String createMoveOverviewForm(@Validated TitleForm titleForm, BindingResult result) {
        if (result.hasErrors()) {
            return createRedoTitle();
        }
        return "utilities/hiddensTag/overviewForm";
    }

    // ---
    // request handlers for overview form.
    // ---
    @RequestMapping(method = RequestMethod.POST, params = "redoTitle")
    public String createRedoTitle() {
        return "utilities/hiddensTag/titleForm";
    }

    @RequestMapping(method = RequestMethod.POST, params = "moveContent")
    public String createMoveContentForm(@Validated OverviewForm overviewForm, BindingResult result) {
        if (result.hasErrors()) {
            return createRedoOverview();
        }
        return "utilities/hiddensTag/contentForm";
    }

    // ---
    // request handlers for content form.
    // ---
    @RequestMapping(method = RequestMethod.POST, params = "redoOverview")
    public String createRedoOverview() {
        return "utilities/hiddensTag/overviewForm";
    }

    @RequestMapping(method = RequestMethod.POST, params = "moveAuthor")
    public String createMoveAuthorForm(@Validated ContentForm overviewForm, BindingResult result) {
        if (result.hasErrors()) {
            return createRedoOverview();
        }
        return "utilities/hiddensTag/authorForm";
    }

    // ---
    // request handlers for author form.
    // ---
    @RequestMapping(method = RequestMethod.POST, params = "redoContent")
    public String createRedoContent() {
        return "utilities/hiddensTag/contentForm";
    }

    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    @RequestMapping(method = RequestMethod.POST, params = "confirm")
    public String createConfirm(@Validated AuthorForm authorForm, BindingResult result) {
        if (result.hasErrors()) {
            return createRedoAuthor();
        }
        return "utilities/hiddensTag/confirmForm";
    }

    // ---
    // request handlers for confirm form.
    // ---
    @RequestMapping(method = RequestMethod.POST, params = "redoAuthor")
    public String createRedoAuthor() {
        return "utilities/hiddensTag/authorForm";
    }

    @TransactionTokenCheck
    @RequestMapping(method = RequestMethod.POST)
    public String create(@Validated ConfirmForm confirmForm, BindingResult confirmFormResult,
            @Validated TitleForm titleForm, BindingResult titleFormResult, @Validated OverviewForm overviewForm,
            BindingResult overviewFormResult, @Validated ContentForm contentForm, BindingResult contentFormResult,
            @Validated AuthorForm authorForm, BindingResult authorFormResult, RedirectAttributes redirectAttributes) {

        // check validation result.
        if (confirmFormResult.hasErrors()) {
            return "utilities/hiddensTag/confirmForm";
        }

        // check validation result of another form.
        if (hasBindingErrors(titleFormResult, overviewFormResult, contentFormResult, authorFormResult)) {
            redirectAttributes.addFlashAttribute(ResultMessages.success().add(
                    Messages.FW_INVALID_REQUEST.getResultMessage()));
            return "redirect:/utilities/hiddensTag/create?complete";
        }

        // convert to domain object from form object.
        Article inputtedArticle = new Article();
        mapToBean(inputtedArticle, titleForm, overviewForm, contentForm, authorForm);

        // execute business logic.
        Article createdArticle = articleSharedService.createArticle(inputtedArticle, false);

        // create models for view.
        redirectAttributes.addFlashAttribute(createdArticle);
        redirectAttributes.addFlashAttribute(ResultMessages.success().add(
                Messages.UT_ARTICLE_CREATED.getResultMessage(createdArticle.getArticleId())));

        return "redirect:/utilities/hiddensTag/create?complete";
    }

    // ---
    // request handlers for complete.
    // ---
    @RequestMapping(method = RequestMethod.GET, params = "complete")
    public String createComplete() {
        return "utilities/hiddensTag/complete";
    }

    private boolean hasBindingErrors(BindingResult... bindingResults) {
        for (BindingResult bindingResult : bindingResults) {
            if (bindingResult.hasErrors()) {
                return true;
            }
        }
        return false;
    }

    private void mapToBean(Object destinationBean, Object... sourceBeans) {
        for (Object sourceBean : sourceBeans) {
            beanMapper.map(sourceBean, destinationBean);
        }
    }

}
