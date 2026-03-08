package com.aman.projects.lovable_clone.service;

import com.aman.projects.lovable_clone.dto.member.InviteMemberRequest;
import com.aman.projects.lovable_clone.dto.member.MemberResponse;
import com.aman.projects.lovable_clone.dto.member.UpdateMemberRoleRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ProjectMemberService {
     List<MemberResponse> getProjectMembers(Long projectId, Long userId);

     MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId);

     MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request, Long userId);

     void removeProjectMember(Long projectId, Long memberId, Long userId);
}
