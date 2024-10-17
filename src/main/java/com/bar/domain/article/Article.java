package com.bar.domain.article;

import com.bar.domain.bar.BarId;
import com.bar.domain.exception.NullBarIdException;
import com.bar.domain.exception.NullNameException;
import com.bar.domain.exception.NullPriceException;
import com.bar.domain.shared.Name;
import com.bar.domain.shared.Price;

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
       indexes = {@Index(name = "idx_article_name_bar", columnList = "name, barId")})
public class Article {

    @EmbeddedId
    private ArticleId id;
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
    @Column(name = "active", nullable = false)
    private boolean active;

    public Article() {
        this.id = new ArticleId();
    }

    public Article(Name name, BarId barId, Price price, boolean active) throws NullNameException {

        this();
        if(name == null) throw new NullNameException();
        this.name = name;
        if(barId == null) throw new NullBarIdException();
        this.barId = barId;
        if(price == null) throw new NullPriceException();
        this.price = price;
        this.active = active;
    }

    public ArticleId getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public BarId getBarId() {
        return barId;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
