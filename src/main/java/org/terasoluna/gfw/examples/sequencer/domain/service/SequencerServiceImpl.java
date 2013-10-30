package org.terasoluna.gfw.examples.sequencer.domain.service;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.sequencer.Sequencer;
import org.terasoluna.gfw.examples.common.domain.model.Article;
import org.terasoluna.gfw.examples.common.domain.repository.ArticleRepository;

@Service
public class SequencerServiceImpl implements SequencerService {

    @Inject
    ArticleRepository articleRepository;

    @Inject
    @Named("articleIdSequencer")
    Sequencer<Long> articleIdSequencer;

    @Transactional
    public Article createArticle(Article article, boolean usingSequencer) {
        if (usingSequencer) {
            article.setArticleId(articleIdSequencer.getNext());
        }
        Article savedArticle = articleRepository.save(article);
        return savedArticle;
    }

}
