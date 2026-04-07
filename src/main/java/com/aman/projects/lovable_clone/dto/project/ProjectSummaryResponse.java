package com.aman.projects.lovable_clone.dto.project;

import com.aman.projects.lovable_clone.enums.ProjectRole;

import java.time.Instant;

public record ProjectSummaryResponse(
        Long id,
        String projectName,
        Instant createdAt,
        Instant updatedAt,
        ProjectRole role
) {
}
