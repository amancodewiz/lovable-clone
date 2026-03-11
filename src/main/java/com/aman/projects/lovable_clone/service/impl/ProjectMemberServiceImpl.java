package com.aman.projects.lovable_clone.service.impl;

import com.aman.projects.lovable_clone.dto.member.InviteMemberRequest;
import com.aman.projects.lovable_clone.dto.member.MemberResponse;
import com.aman.projects.lovable_clone.dto.member.UpdateMemberRoleRequest;
import com.aman.projects.lovable_clone.entity.Project;
import com.aman.projects.lovable_clone.entity.ProjectMember;
import com.aman.projects.lovable_clone.entity.ProjectMemberId;
import com.aman.projects.lovable_clone.entity.User;
import com.aman.projects.lovable_clone.mapper.ProjectMemberMapper;
import com.aman.projects.lovable_clone.repository.ProjectMemberRepository;
import com.aman.projects.lovable_clone.repository.ProjectRepository;
import com.aman.projects.lovable_clone.repository.UserRepository;
import com.aman.projects.lovable_clone.service.ProjectMemberService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
public class ProjectMemberServiceImpl implements ProjectMemberService {

    ProjectMemberRepository projectMemberRepository;
    ProjectRepository projectRepository;
    ProjectMemberMapper projectMemberMapper;
    UserRepository userRepository;

    @Override
    public List<MemberResponse> getProjectMembers(Long projectId, Long userId) {
        Project project = getAccessibleProjectById(projectId, userId);

        return projectMemberRepository.findByIdProjectId(projectId)
                .stream()
                .map(projectMemberMapper::toProjectMemberResponseFromMember)
                .toList();
    }

    @Override
    public MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId) {
        Project project = getAccessibleProjectById(projectId, userId);//getting all the projects

        User invitee = userRepository.findByUsername(request.username()).orElseThrow();

        if (invitee.getId().equals(userId)) {
            throw new RuntimeException("Cannot invite yourself");
        }

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, invitee.getId());

        if (projectMemberRepository.existsById(projectMemberId)) {
            throw new RuntimeException("Already invited cannot invite again");
        }
        //Create the member, save the member and you have invited a new person to your project
        ProjectMember member = ProjectMember.builder()
                .id(projectMemberId)
                .project(project)
                .user(invitee)
                .projectRole(request.role())
                .invitedAt(Instant.now())
                .build();

        projectMemberRepository.save(member);

        return projectMemberMapper.toProjectMemberResponseFromMember(member);
    }

    @Override
    public MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request, Long userId) {
        Project project = getAccessibleProjectById(projectId, userId);//getting all the projects

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, memberId);

        ProjectMember projectMember = projectMemberRepository.findById(projectMemberId).orElseThrow();//This will find the particular project member

        projectMember.setProjectRole(request.role());

        projectMemberRepository.save(projectMember);

        return projectMemberMapper.toProjectMemberResponseFromMember(projectMember);
    }

    @Override
    public void removeProjectMember(Long projectId, Long memberId, Long userId) {
        Project project = getAccessibleProjectById(projectId, userId);//getting all the projects

        //Below 2 lines checks if the project member exists
        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, memberId);
        if (!projectMemberRepository.existsById(projectMemberId)) {
            throw new RuntimeException("Project member does not exist");
        }

        projectMemberRepository.deleteById(projectMemberId);//delete the project member
    }

    /// INTERNAL FUNCTION
    /// Instead of writing the below line again and again we created an internal function and we will only have to call this internal function everywhere
    /// Project project = projectRepository.findAccessibleProjectById(id, userId).orElseThrow();->Project project = getAccessibleProjectById(id, userId);
    /// Made our code dry using this technique
    public Project getAccessibleProjectById(Long projectId, Long userId) {
        return projectRepository.findAccessibleProjectById(projectId, userId).orElseThrow();
    }
}