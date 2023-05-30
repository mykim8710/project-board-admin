package io.mykim.projectboardadmin.todo.api;

import io.mykim.projectboardadmin.config.security.dto.PrincipalDetail;
import io.mykim.projectboardadmin.global.response.dto.CommonResponse;
import io.mykim.projectboardadmin.global.response.enums.CustomSuccessCode;
import io.mykim.projectboardadmin.todo.dto.TodoCreateDto;
import io.mykim.projectboardadmin.todo.entity.TodoStatus;
import io.mykim.projectboardadmin.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TodoApiController {
    private final TodoService todoService;

    @GetMapping("/api/v1/todos")
    public ResponseEntity<CommonResponse> findMyTodoList(@AuthenticationPrincipal PrincipalDetail principalDetail,
                                                         @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("[GET] /api/v1/todos?page={} => find all hashtags", pageable.getOffset());
        CommonResponse response = new CommonResponse(CustomSuccessCode.COMMON_SUCCESS, todoService.findAllMyTodoList(principalDetail.getAdminUser(), pageable));
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    @PostMapping("/api/v1/todos")
    public ResponseEntity<CommonResponse> createNewTodo(@AuthenticationPrincipal PrincipalDetail principalDetail, @RequestBody TodoCreateDto createDto) {
        log.info("[POST] /api/v1/todos => create new todo, TodoCreateDto={}", createDto);
        CommonResponse response = new CommonResponse(CustomSuccessCode.INSERT_SUCCESS, todoService.createTodo(createDto, principalDetail.getAdminUser()));
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    @PatchMapping("/api/v1/todos/{todoId}")
    public ResponseEntity<CommonResponse> updateTodoStatus(@PathVariable Long todoId, @RequestParam TodoStatus todoStatus) {
        log.info("[PATCH] /api/v1/todos/{}?todoStatus={} => update todo status", todoId, todoStatus);
        todoService.updateTodoStatus(todoId, todoStatus);
        CommonResponse response = new CommonResponse(CustomSuccessCode.UPDATE_SUCCESS);
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }


}
