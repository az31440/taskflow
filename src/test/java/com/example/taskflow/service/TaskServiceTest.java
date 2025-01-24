package com.example.taskflow.service;

import com.example.taskflow.entity.Task;
import com.example.taskflow.entity.TaskStatus;
import com.example.taskflow.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Optional;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        task = new Task(1L, "Test Task", TaskStatus.TO_DO, "Test description");
    }

    @Test
    public void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(Collections.singletonList(task));

        assertEquals(1, taskService.getAllTasks().size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void testCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(task);
        assertNotNull(createdTask);
        assertEquals("Test Task", createdTask.getName());
    }

    @Test
    public void testUpdateTaskStatus() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task updatedTask = taskService.updateTaskStatus(1L, TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, updatedTask.getStatus());
    }

    @Test
    public void testGetTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task foundTask = taskService.getTaskById(1L);
        assertNotNull(foundTask);
        assertEquals(1L, foundTask.getId());
    }

    @Test
    public void testUpdateTaskDescription() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task updatedTask = taskService.updateTaskDescription(1L, "Updated description");
        assertEquals("Updated description", updatedTask.getDescription());
    }

    @Test
    public void testDeleteTask() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }
}
