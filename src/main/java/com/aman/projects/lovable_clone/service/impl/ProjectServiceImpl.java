package com.aman.projects.lovable_clone.service.impl;

import com.aman.projects.lovable_clone.dto.project.ProjectRequest;
import com.aman.projects.lovable_clone.dto.project.ProjectResponse;
import com.aman.projects.lovable_clone.dto.project.ProjectSummaryResponse;
import com.aman.projects.lovable_clone.entity.Project;
import com.aman.projects.lovable_clone.entity.ProjectMember;
import com.aman.projects.lovable_clone.entity.ProjectMemberId;
import com.aman.projects.lovable_clone.entity.User;
import com.aman.projects.lovable_clone.enums.ProjectRole;
import com.aman.projects.lovable_clone.error.ResourceNotFoundException;
import com.aman.projects.lovable_clone.mapper.ProjectMapper;
import com.aman.projects.lovable_clone.repository.ProjectMemberRepository;
import com.aman.projects.lovable_clone.repository.ProjectRepository;
import com.aman.projects.lovable_clone.repository.UserRepository;
import com.aman.projects.lovable_clone.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional
public class ProjectServiceImpl implements ProjectService {

    ProjectRepository projectRepository;
    UserRepository userRepository;
    ProjectMapper projectMapper;
    ProjectMemberRepository projectMemberRepository;

    @Override
    public ProjectResponse createProject(ProjectRequest request, Long userId) {
        User owner = userRepository.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User", userId.toString())
        );
        Project project = Project.builder()
                .name(request.name())
                .isPublic(false)
                .build();
        project = projectRepository.save(project);

        //Whenever a project(above code) is created below project member will be created
        ProjectMemberId projectMemberId = new ProjectMemberId(project.getId(), owner.getId());
        ProjectMember projectMember = ProjectMember.builder()
                .id(projectMemberId)
                .projectRole(ProjectRole.OWNER)
                .user(owner)
                .acceptedAt(Instant.now())
                .invitedAt(Instant.now())
                .project(project)
                .build();

        projectMemberRepository.save(projectMember);

        return projectMapper.toProjectResponse(project);
    }

    @Override
    public List<ProjectSummaryResponse> getUserProjects(Long userId) {

        var projects = projectRepository.findAllAccessibleByUser(userId);
        return projectMapper.toListOfProjectSummaryResponse(projects);

    }

    @Override
    public ProjectResponse getProjectById(Long id, Long userId) {
        Project project = getAccessibleProjectById(id, userId);
        return projectMapper.toProjectResponse(project);
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest request, Long userId) {
        Project project = getAccessibleProjectById(id, userId);

        if (request.name() != null) {
            project.setName(request.name());
        }

        project = projectRepository.save(project);

        return projectMapper.toProjectResponse(project);
    }

    @Override
    public void softDelete(Long id, Long userId) {
        Project project = getAccessibleProjectById(id, userId);

        project.setDeletedAt(Instant.now());
        projectRepository.save(project);
    }

    /// INTERNAL FUNCTION
    /// Instead of writing the below line again and again we created an internal function and we will only have to call this internal function everywhere
    /// Project project = projectRepository.findAccessibleProjectById(id, userId).orElseThrow();->Project project = getAccessibleProjectById(id, userId);
    /// Made our code dry using this technique
    public Project getAccessibleProjectById(Long projectId, Long userId) {
        //Here we Passed both arguments separately.
        return projectRepository.findAccessibleProjectById(projectId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", projectId.toString()));
    }
}
