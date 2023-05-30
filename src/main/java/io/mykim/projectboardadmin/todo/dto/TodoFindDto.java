package io.mykim.projectboardadmin.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.mykim.projectboardadmin.todo.entity.Todo;
import io.mykim.projectboardadmin.todo.entity.TodoStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoFindDto {
    private Long id;
    private String todoName;
    private TodoStatus todoStatus;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy/MM/dd HH:mm:ss")
    private LocalDateTime lastModifiedAt;

    @Builder
    private TodoFindDto(Long id, String todoName, TodoStatus todoStatus, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.id = id;
        this.todoName = todoName;
        this.todoStatus = todoStatus;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }

    public static TodoFindDto from(Todo todo) {
        return TodoFindDto.builder()
                            .id(todo.getId())
                            .todoName(todo.getTodoName())
                            .todoStatus(todo.getTodoStatus())
                            .createdAt(todo.getCreatedAt())
                            .lastModifiedAt(todo.getLastModifiedAt())
                            .build();
    }
}
