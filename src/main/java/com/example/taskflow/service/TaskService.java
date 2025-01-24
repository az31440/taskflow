package com.example.taskflow.service;

import com.example.taskflow.entity.Task;
import com.example.taskflow.entity.TaskStatus;
import com.example.taskflow.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Get tasks by status
    public List<Task> getTasksByStatus(String status) {
        TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
        return taskRepository.findByStatus(taskStatus);
    }

    // Save a new task
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // Update task status
    public Task updateTaskStatus(Long id, TaskStatus status) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus(status);
        return taskRepository.save(task);
    }

    // Method to get task by id
    public Task getTaskById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.orElse(null);  // Return the task if found, or null if not found
    }

    public Task updateTaskDescription(Long taskId, String newDescription) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));

        task.setDescription(newDescription);
        return taskRepository.save(task);
    }
    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new RuntimeException("Task not found with ID: " + taskId);
        }
        taskRepository.deleteById(taskId);
    }
}
