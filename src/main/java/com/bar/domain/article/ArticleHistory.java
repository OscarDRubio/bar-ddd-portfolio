package com.bar.domain.article;

import java.time.ZonedDateTime;

import com.bar.domain.shared.DateTimeWithZone;
import com.bar.domain.shared.Name;
import com.bar.domain.shared.Price;

import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;

@Entity
@Table(name = "article_history", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"articleId", "version"}),
       indexes = {@Index(name = "idx_article_version", columnList = "articleId, version")})
public class ArticleHistory {

    @EmbeddedId
    private ArticleHistoryId articleHistoryId;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "articleId", nullable = false))
    })
    private ArticleId articleId;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column(name = "name", nullable = false))
    })
    private Name name;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "price", column = @Column(name = "price", nullable = false))
    })
    private Price price;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "dateTimeWithZone", column = @Column(name = "dateTime", nullable = false))
    })
    private DateTimeWithZone dateTime;

    @Column(name = "version", nullable = false)
    private int version;

    @Column(name = "active", nullable = false)
    private boolean active;

    public ArticleHistoryId getArticleHistoryId() {
        return articleHistoryId;
    }

    public ArticleId getArticleId() {
        return articleId;
    }

    public Name getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public DateTimeWithZone getDateTime() {
        return dateTime;
    }

    public int getVersion() {
        return version;
    }

    public boolean isActive() {
        return active;
    }

    public void setArticleHistoryId(ArticleHistoryId articleHistoryId) {
        this.articleHistoryId = articleHistoryId;
    }

    public void setArticleId(ArticleId articleId) {
        this.articleId = articleId;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public void setDateTime(DateTimeWithZone dateTime) {
        this.dateTime = dateTime;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ArticleHistory() {
        this.articleHistoryId = new ArticleHistoryId();
    }

    public ArticleHistory(Article article) {
        this();
        this.articleId = article.getId();
        this.name = article.getName();
        this.price = article.getPrice();
        this.dateTime = new DateTimeWithZone(ZonedDateTime.now());
        this.active = article.isActive();
    }
}
