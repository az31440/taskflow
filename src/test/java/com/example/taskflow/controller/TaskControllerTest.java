package com.example.taskflow.controller;

import com.example.taskflow.entity.Task;
import com.example.taskflow.entity.TaskStatus;
import com.example.taskflow.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private Task task1, task2;

    @BeforeEach
    void setUp() {
        task1 = new Task(1L, "Task 1", TaskStatus.TO_DO, "Description 1");
        task2 = new Task(2L, "Task 2", TaskStatus.IN_PROGRESS, "Description 2");
    }

    @Test
    void getAllTasks_ShouldReturnAllTasks() {
        when(taskService.getAllTasks()).thenReturn(Arrays.asList(task1, task2));
        List<Task> tasks = taskController.getAllTasks();
        assertEquals(2, tasks.size());
    }

    @Test
    void getTasksByStatus_ShouldReturnFilteredTasks() {
        when(taskService.getTasksByStatus("TO_DO")).thenReturn(List.of(task1));
        List<Task> tasks = taskController.getTasksByStatus("TO_DO");
        assertEquals(1, tasks.size());
        assertEquals(TaskStatus.TO_DO, tasks.get(0).getStatus());
    }

    @Test
    void createTask_ShouldReturnCreatedTask() {
        when(taskService.createTask(any(Task.class))).thenReturn(task1);
        Task createdTask = taskController.createTask(task1);
        assertNotNull(createdTask);
        assertEquals("Task 1", createdTask.getName());
    }

    @Test
    void updateTaskStatus_ShouldReturnUpdatedTask() {
        Task updatedTask = new Task(1L, "Task 1", TaskStatus.IN_PROGRESS, "Description 1");
        when(taskService.updateTaskStatus(1L, TaskStatus.IN_PROGRESS)).thenReturn(updatedTask);
        Task result = taskController.updateTaskStatus(1L, "IN_PROGRESS");
        assertEquals(TaskStatus.IN_PROGRESS, result.getStatus());
        verify(taskService, times(1)).updateTaskStatus(1L, TaskStatus.IN_PROGRESS);
    }


    @Test
    void getTaskById_ShouldReturnTask() {
        when(taskService.getTaskById(1L)).thenReturn(task1);
        Task retrievedTask = taskController.getTaskById(1L);
        assertEquals(1L, retrievedTask.getId());
    }

    @Test
    void updateTaskDescription_ShouldUpdateDescription() {
        when(taskService.updateTaskDescription(1L, "New Description"))
                .thenReturn(new Task(1L, "Task 1", TaskStatus.TO_DO, "New Description"));
        ResponseEntity<Task> response = taskController.updateTaskDescription(1L, "New Description");
        assertEquals("New Description", response.getBody().getDescription());
    }

    @Test
    void deleteTask_ShouldReturnSuccessMessage() {
        doNothing().when(taskService).deleteTask(1L);
        ResponseEntity<String> response = taskController.deleteTask(1L);
        assertEquals("Task with ID 1 deleted successfully.", response.getBody());
    }
}