package com.bar.domain.article;

import com.bar.domain.bar.BarId;
import com.bar.domain.exceptions.NullBarIdException;
import com.bar.domain.shared.Name;
import com.bar.domain.shared.Price;
import com.bar.exception.NullNameException;
import com.bar.exception.NullPriceException;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "article", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"barId", "name"}),
       indexes = {@Index(name = "idx_name_bar", columnList = "name, barId")})
public class Article {

    @EmbeddedId
    private ArticleId articleId;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column(name = "name", nullable = false))
    })
    private Name name;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "barId", nullable = false))
    })
    private BarId barId;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "price", column = @Column(name = "price", nullable = false))
    })
    private Price price;

    public Article() {
        this.articleId = new ArticleId();
    }

    public Article(Name name, BarId barId, Price price) throws NullNameException {

        this();
        if(name == null) throw new NullNameException();
        this.name = name;
        if(barId == null) throw new NullBarIdException();
        this.barId = barId;
        if(price == null) throw new NullPriceException();
        this.price = price;
    }

    public ArticleId getArticleId() {
        return articleId;
    }

    public Name getName() {
        return name;
    }

    public BarId getBarId() {
        return barId;
    }

    public Price getPrice() {
        return price;
    }
}
