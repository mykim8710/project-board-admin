package io.mykim.projectboardadmin.todo.entity;

import io.mykim.projectboardadmin.adminuser.entity.AdminUser;
import io.mykim.projectboardadmin.global.jpa.AuditingField;
import io.mykim.projectboardadmin.todo.dto.TodoCreateDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "admin_todo")
public class Todo extends AuditingField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;
    @Column(name = "todo_name", nullable = false, length = 100)
    private String todoName;
    @Enumerated(EnumType.STRING)
    @Column(name = "todo_status", length = 50)
    private TodoStatus todoStatus;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_user_id", nullable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private AdminUser adminUser;


    private Todo(String todoName, TodoStatus todoStatus, AdminUser adminUser) {
        this.todoName = todoName;
        this.todoStatus = todoStatus;
        this.adminUser = adminUser;
    }

    public static Todo of(TodoCreateDto createDto, AdminUser adminUser) {
        return new Todo(createDto.getTodoName(), TodoStatus.NOT, adminUser);
    }

    public void updateTodoStatus(TodoStatus todoStatus) {
        this.todoStatus = todoStatus;
    }
}
