package com.bar.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bar.domain.article.Article;
import com.bar.domain.article.ArticleHistory;
import com.bar.infrastructure.repository.article.ArticleHistoryRepository;
import com.bar.infrastructure.repository.article.ArticleRepository;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleHistoryRepository articleHistoryRepository;

    @Transactional
    public Article createOrUpdateArticle(Article article) {

        List<ArticleHistory> latestArticleHistories = articleHistoryRepository.findAllByArticleId(article.getId());

        int maxVersion = latestArticleHistories.stream()
            .map(ArticleHistory::getVersion)
            .max(Integer::compareTo)
            .orElse(0); 
        maxVersion++;

        articleRepository.save(article);

        ArticleHistory articleHistory = new ArticleHistory(article);
        articleHistory.setVersion(maxVersion);
        articleHistoryRepository.save(articleHistory);

        return article;
    }
}
