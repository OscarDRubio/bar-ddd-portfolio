package com.bar.infrastructure.repository.article;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bar.domain.article.ArticleHistory;
import com.bar.domain.article.ArticleHistoryId;
import com.bar.domain.article.ArticleId;

@Repository
public interface ArticleHistoryRepository extends JpaRepository<ArticleHistory, ArticleHistoryId> {
    
    List<ArticleHistory> findAllByArticleId(ArticleId articleId);
}
