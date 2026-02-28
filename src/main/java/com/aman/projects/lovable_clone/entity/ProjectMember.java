package com.aman.projects.lovable_clone.entity;

import com.aman.projects.lovable_clone.enums.ProjectRole;

import java.time.Instant;

//ProjectMember: Helps join user table and member table
public class ProjectMember {

    ProjectMemberId id;
    Project project;
    User user;
    ProjectRole projectRole;

    Instant invitedAt;
    Instant acceptedAt;
}
