package com.aman.projects.lovable_clone.entity;

import com.aman.projects.lovable_clone.enums.ProjectRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "project_members")
//ProjectMember: Helps join user table and member table
public class ProjectMember {

    @EmbeddedId
    //Create composite key in our table
    ProjectMemberId id;

    @ManyToOne
    @MapsId("projectId") //Reason for using mapsId => donot create new column the below project should be mapped to the projectId in ProjectMemberId
    Project project;

    @ManyToOne
    @MapsId("userId")
    User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    ProjectRole projectRole;

    Instant invitedAt;
    Instant acceptedAt;
}
