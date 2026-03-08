package com.aman.projects.lovable_clone.dto.member;

import com.aman.projects.lovable_clone.dto.project.ProjectResponse;
import com.aman.projects.lovable_clone.enums.ProjectRole;

import java.time.Instant;

public record MemberResponse(
        Long userId,
        String email,
        String name,
        ProjectRole projectRole,
        Instant invitedAt

) {
}
