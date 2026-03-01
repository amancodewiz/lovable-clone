package com.aman.projects.lovable_clone.dto.member;

import com.aman.projects.lovable_clone.dto.project.ProjectResponse;

import java.time.Instant;

public record MemberResponse(
        Long userId,
        String email,
        String name,
        String avatarUrl,
        ProjectResponse role,
        Instant invitedAt

) {
}
