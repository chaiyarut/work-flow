package com.example.workflow.data.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "task")
public class TaskEntity {

    @Id
    @Column(name = "taskId")
    private String taskId;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "task_date")
    private LocalDate taskDate;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "is_assign")
    private Boolean isAssign;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TaskEntity that = (TaskEntity) o;
        return taskId != null && Objects.equals(taskId, that.taskId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
