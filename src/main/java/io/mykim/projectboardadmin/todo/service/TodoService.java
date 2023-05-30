package io.mykim.projectboardadmin.todo.service;

import io.mykim.projectboardadmin.adminuser.entity.AdminUser;
import io.mykim.projectboardadmin.global.pageable.CustomPaginationResponse;
import io.mykim.projectboardadmin.global.response.enums.CustomErrorCode;
import io.mykim.projectboardadmin.global.response.exception.NotFoundExceptionException;
import io.mykim.projectboardadmin.todo.dto.TodoCreateDto;
import io.mykim.projectboardadmin.todo.dto.TodoFindDto;
import io.mykim.projectboardadmin.todo.dto.TodoListDto;
import io.mykim.projectboardadmin.todo.entity.Todo;
import io.mykim.projectboardadmin.todo.entity.TodoStatus;
import io.mykim.projectboardadmin.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class TodoService {
    private final TodoRepository todoRepository;

    @Transactional(readOnly = true)
    public TodoListDto findAllMyTodoList(AdminUser adminUser, Pageable pageable) {
        Page<Todo> todos = todoRepository.findAllByAdminUserId(adminUser.getId(), pageable);
        List<TodoFindDto> todoFindDtos = todos.getContent()
                                                    .stream()
                                                    .map(todo -> TodoFindDto.from(todo))
                                                    .collect(Collectors.toList());

        return TodoListDto.builder()
                            .todoFindDtos(todoFindDtos)
                .customPaginationResponse(CustomPaginationResponse.of(todos.getTotalElements(), todos.getTotalPages(), todos.getNumber()))
                            .build();
    }


    @Transactional
    public Long createTodo(TodoCreateDto createDto, AdminUser adminUser) {
        Todo todo = Todo.of(createDto, adminUser);
        return todoRepository.save(todo).getId();
    }

    @Transactional
    public void updateTodoStatus(Long todoId, TodoStatus todoStatus) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new NotFoundExceptionException(CustomErrorCode.NOT_FOUND_TODO));
        todo.updateTodoStatus(todoStatus);
    }
}
