package com.example.taskflow.service;

import com.example.taskflow.entity.Task;
import com.example.taskflow.entity.TaskAssignment;
import com.example.taskflow.entity.User;
import com.example.taskflow.repository.TaskAssignmentRepository;
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
class TaskAssignmentServiceTest {

    @Mock
    private TaskAssignmentRepository taskAssignmentRepository;

    @InjectMocks
    private TaskAssignmentService taskAssignmentService;

    private TaskAssignment assignment1, assignment2;

    @BeforeEach
    void setUp() {
        User user1 = new User();
        user1.setId(1L);

        Task task1 = new Task();
        task1.setId(101L);

        assignment1 = new TaskAssignment();
        assignment1.setId(1L);
        assignment1.setTask(task1);
        assignment1.setAssignee(user1);

        User user2 = new User();
        user2.setId(2L);

        Task task2 = new Task();
        task2.setId(102L);

        assignment2 = new TaskAssignment();
        assignment2.setId(2L);
        assignment2.setTask(task2);
        assignment2.setAssignee(user2);
    }

    @Test
    void testGetAllTaskAssignments() {
        when(taskAssignmentRepository.findAll()).thenReturn(Arrays.asList(assignment1, assignment2));

        List<TaskAssignment> assignments = taskAssignmentService.getAllTaskAssignments();

        assertEquals(2, assignments.size());
        verify(taskAssignmentRepository, times(1)).findAll();
    }

    @Test
    void testCreateTaskAssignment() {
        when(taskAssignmentRepository.save(any(TaskAssignment.class))).thenReturn(assignment1);

        TaskAssignment createdAssignment = taskAssignmentService.createTaskAssignment(assignment1);

        assertNotNull(createdAssignment);
        assertEquals(assignment1.getId(), createdAssignment.getId());
        verify(taskAssignmentRepository, times(1)).save(any(TaskAssignment.class));
    }

    @Test
    void testDeleteTaskAssignment() {
        doNothing().when(taskAssignmentRepository).deleteById(1L);

        assertDoesNotThrow(() -> taskAssignmentService.deleteTaskAssignment(1L));

        verify(taskAssignmentRepository, times(1)).deleteById(1L);
    }
}
