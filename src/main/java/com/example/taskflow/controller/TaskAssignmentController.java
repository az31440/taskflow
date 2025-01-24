package com.example.taskflow.controller;

import com.example.taskflow.entity.TaskAssignment;
import com.example.taskflow.service.TaskAssignmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task-assignments")
public class TaskAssignmentController {
    private final TaskAssignmentService taskAssignmentService;

    public TaskAssignmentController(TaskAssignmentService taskAssignmentService) {
        this.taskAssignmentService = taskAssignmentService;
    }

    @GetMapping
    public List<TaskAssignment> getAllTaskAssignments() {
        return taskAssignmentService.getAllTaskAssignments();
    }

    @PostMapping
    public TaskAssignment createTaskAssignment(@RequestBody TaskAssignment taskAssignment) {
        return taskAssignmentService.createTaskAssignment(taskAssignment);
    }

    @DeleteMapping("/{id}")
    public void deleteTaskAssignment(@PathVariable Long id) {
        taskAssignmentService.deleteTaskAssignment(id);
    }
}
