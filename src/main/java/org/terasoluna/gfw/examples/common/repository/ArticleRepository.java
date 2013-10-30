package org.terasoluna.gfw.examples.common.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.terasoluna.gfw.examples.common.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findPageByTitleStartingWith(String title, Pageable pageable);

    Page<Article> findPageByTitleStartingWithAndPublishedDate(String title, Date publishedDate, Pageable pageable);

}
