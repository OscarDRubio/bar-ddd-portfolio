package com.bar.domain.bar;

import com.bar.domain.exception.NullNameException;
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

    private Bar() {
        this.id = new BarId();
    }

    public Bar(Name name) {
        
        this();

        if(name == null) throw new NullNameException();
        this.name = name;
    }

    public BarId getId() {
        return id;
    }

    public void setId(BarId id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }
}
