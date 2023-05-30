package io.mykim.projectboardadmin.todo.repository;

import io.mykim.projectboardadmin.todo.entity.Todo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Slice<Todo> findAllByAdminUserId(Long adminUserId, Pageable pageable);
}
