package com.bar.domain.bar;

import com.bar.domain.exception.NullNameException;
import com.bar.domain.shared.Name;
import jakarta.persistence.*;

@Entity
@Table(name="bar", indexes = {@Index(name = "idx_name", columnList = "name")})
public class Bar {

    //TODO: Hacer pruebas en MongoDB también
    //TODO: No me está reconociendo la id
    @EmbeddedId
    private BarId id;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column(name = "name", unique = true, nullable = false))
    })
    private Name name;

    private Bar() {}

    public Bar(Name name) {
        this(null, name);
    }

    public Bar(BarId id, Name name) {
        
        this.id = id == null ? new BarId() : id;

        if(name == null) throw new NullNameException();
        this.name = name;
    }

    public BarId getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public BarDto toDto() {
        return new BarDto(this.id.toString(), this.name.toString());
    }
}
