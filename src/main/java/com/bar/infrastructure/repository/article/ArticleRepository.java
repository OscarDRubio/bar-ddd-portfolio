package com.bar.infrastructure.repository.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bar.domain.article.Article;
import com.bar.domain.article.ArticleId;

@Repository
public interface ArticleRepository extends JpaRepository<Article, ArticleId> {
}
