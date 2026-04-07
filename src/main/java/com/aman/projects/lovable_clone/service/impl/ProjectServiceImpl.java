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
import com.aman.projects.lovable_clone.security.AuthUtil;
import com.aman.projects.lovable_clone.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
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
    AuthUtil authUtil;


    @Override
    public ProjectResponse createProject(ProjectRequest request) {
        Long userId = authUtil.getCurrentUserId();
//        User owner = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException(
//                        "User not found with id: " + userId,
//                        "USER"
//                ));

        //the above commented part will make a db call while the below will not make a db call
        //Below creates a hibernate proxy object, also it should be inside @Transactional context
        User owner=userRepository.getReferenceById(userId);

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
    public List<ProjectSummaryResponse> getUserProjects() {
        Long userId = authUtil.getCurrentUserId();
        var projects = projectRepository.findAllAccessibleByUser(userId);
        return projectMapper.toListOfProjectSummaryResponse(projects);

    }

    @Override
    @PreAuthorize("@Security.canViewProject(#projectId)")
    public ProjectResponse getUserProjectById(Long projectId) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId, userId);
        return projectMapper.toProjectResponse(project);
    }

    @Override
    @PreAuthorize("@Security.canEditProject(#projectId)")
    public ProjectResponse updateProject(Long projectId, ProjectRequest request) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId, userId);

        if (request.name() != null) {
            project.setName(request.name());
        }

        project = projectRepository.save(project);

        return projectMapper.toProjectResponse(project);
    }

    @Override
    @PreAuthorize("@Security.canDeleteProject(#projectId)")
    public void softDelete(Long projectId) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId, userId);

        project.setDeletedAt(Instant.now());
        projectRepository.save(project);
    }

    /// INTERNAL FUNCTION
    /// Instead of writing the below line again and again we created an internal function and we will only have to call this internal function everywhere
    /// Project project = projectRepository.findAccessibleProjectById(id, userId).orElseThrow();->Project project = getAccessibleProjectById(id, userId);
    /// Made our code dry using this technique
    public Project getAccessibleProjectById(Long projectId, Long userId) {
        return projectRepository.findAccessibleProjectById(projectId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Project not found with id: " + projectId,
                        "PROJECT"
                ));
    }
}
