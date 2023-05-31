package io.mykim.projectboardadmin.todo.dto;

import io.mykim.projectboardadmin.global.pageable.CustomPaginationResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class TodoListDto {
    private List<TodoFindDto> todoFindDtos;
    private CustomPaginationResponse customPaginationResponse;

    @Builder

    public TodoListDto(List<TodoFindDto> todoFindDtos, CustomPaginationResponse customPaginationResponse) {
        this.todoFindDtos = todoFindDtos;
        this.customPaginationResponse = customPaginationResponse;
    }
}
