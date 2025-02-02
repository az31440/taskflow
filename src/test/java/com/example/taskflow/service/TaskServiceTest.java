package com.example.taskflow.service;

import com.example.taskflow.entity.Task;
import com.example.taskflow.entity.TaskStatus;
import com.example.taskflow.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task1, task2;

    @BeforeEach
    void setUp() {
        task1 = new Task(1L, "Task 1", TaskStatus.TO_DO, "Description 1");
        task2 = new Task(2L, "Task 2", TaskStatus.IN_PROGRESS, "Description 2");
    }

    @Test
    void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        List<Task> tasks = taskService.getAllTasks();

        assertEquals(2, tasks.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetTasksByStatus() {
        when(taskRepository.findByStatus(TaskStatus.TO_DO)).thenReturn(List.of(task1));

        List<Task> tasks = taskService.getTasksByStatus("TO_DO");

        assertEquals(1, tasks.size());
        assertEquals(TaskStatus.TO_DO, tasks.get(0).getStatus());
    }

    @Test
    void testCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task1);

        Task createdTask = taskService.createTask(task1);

        assertNotNull(createdTask);
        assertEquals("Task 1", createdTask.getName());
    }

    @Test
    void testUpdateTaskStatus() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
        when(taskRepository.save(any(Task.class))).thenReturn(task1);

        Task updatedTask = taskService.updateTaskStatus(1L, TaskStatus.COMPLETED);

        assertEquals(TaskStatus.COMPLETED, updatedTask.getStatus());
    }

    @Test
    void testGetTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));

        Task foundTask = taskService.getTaskById(1L);

        assertNotNull(foundTask);
        assertEquals(1L, foundTask.getId());
    }

    @Test
    void testUpdateTaskDescription() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
        when(taskRepository.save(any(Task.class))).thenReturn(task1);

        Task updatedTask = taskService.updateTaskDescription(1L, "New Description");

        assertEquals("New Description", updatedTask.getDescription());
    }

    @Test
    void testDeleteTask() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(1L);

        assertDoesNotThrow(() -> taskService.deleteTask(1L));
        verify(taskRepository, times(1)).deleteById(1L);
    }
}
