package com.aman.projects.lovable_clone.controller;

import com.aman.projects.lovable_clone.dto.project.ProjectRequest;
import com.aman.projects.lovable_clone.dto.project.ProjectResponse;
import com.aman.projects.lovable_clone.dto.project.ProjectSummaryResponse;
import com.aman.projects.lovable_clone.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    //Get Projects of the user
    public ResponseEntity<List<ProjectSummaryResponse>> getMyProjects(){
        Long userId=1L;//TODO: update later with real Spring Security
        return ResponseEntity.ok(projectService.getUserProjects(userId));
    }

    @GetMapping("/{id}")
    //Get Projects of the user by id
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id){
        Long userId=1L;
        return ResponseEntity.ok(projectService.getProjectById(id,userId));
    }

    @PostMapping
    //Create a project
    public ResponseEntity<ProjectResponse> createProject(@RequestBody @Valid ProjectRequest request){
        Long userId=1L;
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(request, userId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @RequestBody @Valid ProjectRequest request){
        Long userId=1L;
        return ResponseEntity.ok(projectService.updateProject(id, request, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProjectResponse> deleteProject(@PathVariable Long id){
        Long userId=1L;
        projectService.softDelete(id, userId);
        return ResponseEntity.noContent().build();
    }
}
