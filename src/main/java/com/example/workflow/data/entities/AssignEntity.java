package com.example.workflow.data.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "assign")
public class AssignEntity {
    @Id
    @Column(name = "assign_id")
    private int assignId;

    @Column(name = "assign_level")
    private String assignLevel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AssignEntity that = (AssignEntity) o;
        return Objects.equals(assignId, that.assignId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
