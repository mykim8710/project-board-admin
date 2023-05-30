package io.mykim.projectboardadmin.todo.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class TodoListDto {
    private List<TodoFindDto> todoFindDtos;
    private int page;            // 현재 페이지 번호
    private boolean hasNextPage; // 다음 페이지 존재 여부
    private boolean isLast;      // 현재 페이지가 마지막 페이지인지 여부

    @Builder
    public TodoListDto(List<TodoFindDto> todoFindDtos, int page, boolean hasNextPage, boolean isLast) {
        this.todoFindDtos = todoFindDtos;
        this.page = page;
        this.hasNextPage = hasNextPage;
        this.isLast = isLast;
    }
}
