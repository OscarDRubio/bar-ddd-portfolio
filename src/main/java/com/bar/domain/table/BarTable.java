package com.bar.domain.table;

import com.bar.domain.bar.BarId;
import com.bar.domain.exception.NullBarIdException;
import com.bar.domain.exception.NullNameException;
import com.bar.domain.shared.Name;
import jakarta.persistence.*;

@Entity
@Table(name = "bar_table", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"barId", "name"}),
       indexes = {@Index(name = "idx_name_bar", columnList = "name, barId")})
public class BarTable {

    @EmbeddedId
    private BarTableId id;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "barId", nullable = false))
    })
    private BarId barId;
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column(name = "name", nullable = false))
    })
    private Name name;

    public BarTable() {
        this.id = new BarTableId();
    }

    public BarTable(Name name, BarId barId) {
        this();
        if(name == null) throw new NullNameException();
        this.name = name;
        if(barId == null) throw new NullBarIdException();
        this.barId = barId;
    }

    public String getId() {
        return id.toString();
    }

    public void setId(String id) {
        this.id = new BarTableId(id);
    }

    public String getName() {
        return name.toString();
    }

    public void setName(String name) {
        this.name = new Name(name);
    }

    public String getBarId() {
        return barId.toString();
    }

    public void setBarId(BarId barId) {
        this.barId = barId;
    }
}
