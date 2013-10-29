package org.terasoluna.gfw.examples.pagination.domain.service;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.terasoluna.gfw.examples.pagination.domain.model.Article;

public interface PaginationService {

    Page<Article> findAllByCriteria(String title, Date publishedDate, Pageable pageable);

}
