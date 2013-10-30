package org.terasoluna.gfw.examples.sequencer.domain.service;

import org.terasoluna.gfw.examples.common.domain.model.Article;

public interface SequencerService {

    Article createArticle(Article article, boolean usingSequencer);

}
