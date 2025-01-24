package com.example.taskflow.controller;

import com.example.taskflow.entity.Task;
import com.example.taskflow.entity.TaskStatus;
import com.example.taskflow.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Get all tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    // Get tasks by status (To Do, In Progress, Completed)
    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable String status) {
        return taskService.getTasksByStatus(status);
    }

    // Create a new task
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    // Update task status
    @PutMapping("/{id}/status")
    public Task updateTaskStatus(@PathVariable Long id, @RequestParam String status) {
        TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
        return taskService.updateTaskStatus(id, taskStatus);
    }

    // Method to get task by id
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }
    @PatchMapping("/{id}/description")
    public ResponseEntity<Task> updateTaskDescription(@PathVariable Long id, @RequestBody String newDescription) {
        Task updatedTask = taskService.updateTaskDescription(id, newDescription);
        return ResponseEntity.ok(updatedTask);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task with ID " + id + " deleted successfully.");
    }
}
