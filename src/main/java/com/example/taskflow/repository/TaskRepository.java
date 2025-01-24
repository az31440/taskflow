package com.example.taskflow.repository;

import com.example.taskflow.entity.Task;
import com.example.taskflow.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // Find tasks by status
    List<Task> findByStatus(TaskStatus status);
}
