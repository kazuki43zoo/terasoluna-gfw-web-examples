package org.terasoluna.gfw.examples.pagination.app;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.terasoluna.gfw.common.message.ResultMessage;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.examples.pagination.domain.model.Article;
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
    public String search(@Validated SearchForm form, BindingResult bindingResult,
            @PageableDefault(size = 10) Pageable pageable, Model model) {

        if (bindingResult.hasErrors()) {
            return "pagination/search";
        }

        Page<Article> page = paginationService.findAllByCriteria(form.getTitle(), form.getPublishedDate(), pageable);
        model.addAttribute("page", page);

        if (page.getTotalPages() == 0) {
            model.addAttribute(ResultMessages.info().add(ResultMessage.fromText("Data not found.")));
        }

        return "pagination/search";
    }
}
