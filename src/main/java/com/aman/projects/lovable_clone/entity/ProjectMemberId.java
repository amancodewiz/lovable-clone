package com.aman.projects.lovable_clone.entity;

import jakarta.persistence.Embeddable;
import lombok.*;


@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//This contains PK for ProjectMember
public class ProjectMemberId {
    Long projectId;
    Long userId;
}
