package com.bar.domain.bar;

import com.bar.domain.shared.Name;
import jakarta.persistence.*;

@Entity
@Table(name="bar", indexes = {@Index(name = "idx_name", columnList = "name")})
public class Bar {

    @EmbeddedId
    private BarId id;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column(name = "name", unique = true, nullable = false))
    })
    private Name name;

    public Bar() {
        this.id = new BarId();
    }

    public Bar(String name) {
        this();
        this.name = new Name(name);
    }

    public BarId getId() {
        return id;
    }

    public void setId(BarId id) {
        this.id = id;
    }

    public String getName() {
        return name.toString();
    }

    public void setName(String name) {
        this.name = new Name(name);
    }
}
