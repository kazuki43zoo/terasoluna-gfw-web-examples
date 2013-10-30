package org.terasoluna.gfw.examples.pagination.domain.service;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.query.QueryEscapeUtils;
import org.terasoluna.gfw.examples.common.domain.model.Article;
import org.terasoluna.gfw.examples.common.domain.repository.ArticleRepository;

@Service
public class PaginationServiceImpl implements PaginationService {

    @Inject
    private ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<Article> findPageByCriteria(String title, Date publishedDate, Pageable pageable) {
        if (publishedDate != null) {
            return articleRepository.findPageByTitleStartingWithAndPublishedDate(
                    QueryEscapeUtils.toLikeCondition(title), publishedDate, pageable);
        } else {
            return articleRepository.findPageByTitleStartingWith(QueryEscapeUtils.toLikeCondition(title), pageable);
        }
    }

}
