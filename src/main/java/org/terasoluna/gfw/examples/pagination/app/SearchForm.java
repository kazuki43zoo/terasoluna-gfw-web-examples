package org.terasoluna.gfw.examples.pagination.app;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.terasoluna.gfw.common.codelist.ExistInCodeList;

public class SearchForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 1, max = 30)
    private String title;

    @Past
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date publishedDate;

    @ExistInCodeList(codeListId = "CL_ARTICLE_SEARCH_MAX_DISPLAY_NUMBER")
    private String size;

    @ExistInCodeList(codeListId = "CL_ARTICLE_SEARCH_DEFAULT_SORT")
    private String sort;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

}
