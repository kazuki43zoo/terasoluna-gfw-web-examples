package org.terasoluna.gfw.examples.common.domain.service;

import org.terasoluna.gfw.examples.common.domain.model.Article;

public interface ArticleSharedService {

    Article createArticle(Article article, boolean usingSequencer);

}
