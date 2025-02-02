package com.example.taskflow.service;

import com.example.taskflow.entity.Project;
import com.example.taskflow.repository.ProjectRepository;
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
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    private Project project1, project2;

    @BeforeEach
    void setUp() {
        project1 = new Project();
        project1.setId(1L);
        project1.setName("Project Alpha");

        project2 = new Project();
        project2.setId(2L);
        project2.setName("Project Beta");
    }

    @Test
    void testGetAllProjects() {
        when(projectRepository.findAll()).thenReturn(Arrays.asList(project1, project2));

        List<Project> projects = projectService.getAllProjects();

        assertEquals(2, projects.size());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void testGetProjectById() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project1));

        Project project = projectService.getProjectById(1L);

        assertNotNull(project);
        assertEquals("Project Alpha", project.getName());
        verify(projectRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateProject() {
        when(projectRepository.save(any(Project.class))).thenReturn(project1);

        Project createdProject = projectService.createProject(project1);

        assertNotNull(createdProject);
        assertEquals("Project Alpha", createdProject.getName());
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void testUpdateProject() {
        Project updatedDetails = new Project();
        updatedDetails.setName("Updated Alpha");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project1));
        when(projectRepository.save(any(Project.class))).thenReturn(updatedDetails);

        Project updatedProject = projectService.updateProject(1L, updatedDetails);

        assertNotNull(updatedProject);
        assertEquals("Updated Alpha", updatedProject.getName());
        verify(projectRepository, times(1)).save(project1);
    }

    @Test
    void testDeleteProject() {
        doNothing().when(projectRepository).deleteById(1L);

        assertDoesNotThrow(() -> projectService.deleteProject(1L));

        verify(projectRepository, times(1)).deleteById(1L);
    }
}
