package com.aman.projects.lovable_clone.mapper;

import com.aman.projects.lovable_clone.dto.project.ProjectResponse;
import com.aman.projects.lovable_clone.dto.project.ProjectSummaryResponse;
import com.aman.projects.lovable_clone.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectResponse toProjectResponse(Project project);//This converts ProjectResponse into Project

    @Mapping(source = "name", target="projectName")
    ProjectSummaryResponse toProjectSummaryResponse(Project project);

    List<ProjectSummaryResponse> toListOfProjectSummaryResponse(List<Project> projects);
}
