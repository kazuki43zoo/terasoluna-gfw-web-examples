package org.terasoluna.gfw.examples.pagination.app;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.examples.common.Messages;
import org.terasoluna.gfw.examples.common.domain.model.Article;
import org.terasoluna.gfw.examples.pagination.domain.service.PaginationService;

@RequestMapping("pagination")
@Controller
public class PaginationController {

    @Inject
    private PaginationService paginationService;

    @ModelAttribute
    public SearchForm setUpForm() {
        return new SearchForm();
    }

    @RequestMapping(value = "search", method = RequestMethod.GET, params = "form")
    public String searchForm() {
        return "pagination/search";
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String search(@Validated SearchForm form, BindingResult bindingResult, Pageable pageable, Model model) {

        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("size") || bindingResult.hasFieldErrors("sort")) {
                model.addAttribute(ResultMessages.error().add(Messages.FW_INVALID_REQUEST.getResultMessage()));
            }
            return "pagination/search";
        }

        Page<Article> page = paginationService.findPageByCriteria(form.getTitle(), form.getPublishedDate(), pageable);
        model.addAttribute("page", page);

        if (page.getTotalPages() == 0) {
            model.addAttribute(ResultMessages.info().add(Messages.PA_DATE_NOT_FOUND.getResultMessage()));
        }

        return "pagination/search";
    }
}
