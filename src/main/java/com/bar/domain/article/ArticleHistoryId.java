package com.bar.domain.article;

import com.bar.domain.shared.UuidIdentifier;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.persistence.Embeddable;

@Embeddable
public record ArticleHistoryId(String id) {

    public ArticleHistoryId() {
        this(new UuidIdentifier().toString());
    }

    @JsonValue
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}