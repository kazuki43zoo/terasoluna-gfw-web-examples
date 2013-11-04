package org.terasoluna.gfw.examples.common.domain.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.terasoluna.gfw.examples.common.domain.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    String QUERY_FIND_PAGE_BY_TITLE = "SELECT a FROM Article a WHERE a.title LIKE :title% ESCAPE '~'";

    @Query(QUERY_FIND_PAGE_BY_TITLE)
    Page<Article> findPageByTitleStartingWith(@Param("title") String title, Pageable pageable);

    @Query(QUERY_FIND_PAGE_BY_TITLE + " AND a.publishedDate = :publishedDate")
    Page<Article> findPageByTitleStartingWithAndPublishedDate(@Param("title") String title,
            @Param("publishedDate") Date publishedDate, Pageable pageable);

}
