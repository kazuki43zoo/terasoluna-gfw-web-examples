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
import org.terasoluna.gfw.examples.common.domain.model.Article;
import org.terasoluna.gfw.examples.common.domain.service.ArticleSharedService;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;

@TransactionTokenCheck("utilities/hiddensTag")
@RequestMapping("utilities/hiddensTag")
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
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createForm() {
        return "utilities/hiddensTag/titleForm";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST, params = "moveOverview")
    public String createMoveOverviewForm(@Validated TitleForm titleForm, BindingResult result) {
        if (result.hasErrors()) {
            return createRedoTitle();
        }
        return "utilities/hiddensTag/overviewForm";
    }

    // ---
    // request handlers for overview form.
    // ---
    @RequestMapping(value = "create", method = RequestMethod.POST, params = "redoTitle")
    public String createRedoTitle() {
        return "utilities/hiddensTag/titleForm";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST, params = "moveContent")
    public String createMoveContentForm(@Validated OverviewForm overviewForm, BindingResult result) {
        if (result.hasErrors()) {
            return createRedoOverview();
        }
        return "utilities/hiddensTag/contentForm";
    }

    // ---
    // request handlers for content form.
    // ---
    @RequestMapping(value = "create", method = RequestMethod.POST, params = "redoOverview")
    public String createRedoOverview() {
        return "utilities/hiddensTag/overviewForm";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST, params = "moveAuthor")
    public String createMoveAuthorForm(@Validated ContentForm overviewForm, BindingResult result) {
        if (result.hasErrors()) {
            return createRedoOverview();
        }
        return "utilities/hiddensTag/authorForm";
    }

    // ---
    // request handlers for author form.
    // ---
    @RequestMapping(value = "create", method = RequestMethod.POST, params = "redoContent")
    public String createRedoContent() {
        return "utilities/hiddensTag/contentForm";
    }

    @TransactionTokenCheck(value = "create", type = TransactionTokenType.BEGIN)
    @RequestMapping(value = "create", method = RequestMethod.POST, params = "confirm")
    public String createConfirm(@Validated AuthorForm authorForm, BindingResult result) {
        if (result.hasErrors()) {
            return createRedoAuthor();
        }
        return "utilities/hiddensTag/confirmForm";
    }

    // ---
    // request handlers for confirm form.
    // ---
    @RequestMapping(value = "create", method = RequestMethod.POST, params = "redoAuthor")
    public String createRedoAuthor() {
        return "utilities/hiddensTag/authorForm";
    }

    @TransactionTokenCheck(value = "create")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Validated ConfirmForm confirmForm, BindingResult confirmFormResult,
            @Validated TitleForm titleForm, BindingResult titleFormResult, @Validated OverviewForm overviewForm,
            BindingResult overviewFormResult, @Validated ContentForm contentForm, BindingResult contentFormResult,
            @Validated AuthorForm authorForm, BindingResult authorFormResult, RedirectAttributes redirectAttributes) {

        // check validation result.
        if (confirmFormResult.hasErrors()) {
            return "utilities/hiddensTag/confirmForm";
        }

        // check validation result.
        if (hasError(titleFormResult, overviewFormResult, contentFormResult, authorFormResult)) {
            redirectAttributes.addFlashAttribute(ResultMessages.error().add("e.ex.fw.7003"));
            return "redirect:/utilities/hiddensTag/create?complete";
        }

        // convert to domain object from form object.
        Article inputtedArticle = new Article();
        mapBean(inputtedArticle, titleForm, overviewForm, contentForm, authorForm);

        // execute business logic.
        Article createdArticle = articleSharedService.createArticle(inputtedArticle, false);

        // create models for view.
        redirectAttributes.addFlashAttribute(createdArticle);
        redirectAttributes.addFlashAttribute(ResultMessages.success()
                .add("i.ex.ut.0001", createdArticle.getArticleId()));

        return "redirect:/utilities/hiddensTag/create?complete";
    }

    // ---
    // request handlers for complete.
    // ---
    @RequestMapping(value = "create", method = RequestMethod.GET, params = "complete")
    public String createComplete() {
        return "utilities/hiddensTag/complete";
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String testForm(RootForm rootForm) {
        return "utilities/hiddensTag/testForm";
    }

    @RequestMapping(value = "test", method = RequestMethod.POST, params = "redo")
    public String testRedo(RootForm rootForm) {
        return "utilities/hiddensTag/testForm";
    }

    @RequestMapping(value = "test", method = RequestMethod.POST, params = "confirm")
    public String testConfirm(@Validated RootForm rootForm, BindingResult result) {
        if (result.hasErrors()) {
            return testRedo(rootForm);
        }
        return "utilities/hiddensTag/testConfirm";
    }

    private boolean hasError(BindingResult... results) {
        for (BindingResult result : results) {
            if (result.hasErrors()) {
                return true;
            }
        }
        return false;
    }

    private void mapBean(Object destination, Object... sources) {
        for (Object source : sources) {
            beanMapper.map(source, destination);
        }
    }

}
