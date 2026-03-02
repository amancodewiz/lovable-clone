package com.aman.projects.lovable_clone.controller;


import com.aman.projects.lovable_clone.dto.member.InviteMemberRequest;
import com.aman.projects.lovable_clone.dto.member.MemberResponse;
import com.aman.projects.lovable_clone.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}/members")
public class ProjectMemberController {


    private final ProjectMemberService projectMemberService;//This is dependecy Injection=>Constructor DI=>@RequiredArgsConstructor this creates a constructor on our behalf in the compiled class=>when we use final we make this immutable
    //when using @autowired remove final from here then this will be field DI=>private ProjectMemberService projectMemberService;

    //If I want to remove @RequiredArgsConstructor, we can still use constructor DI by making below constructor ourselves
    /*
    public ProjectMemberController(ProjectMemberService projectMemberService){
        this.projectMemberService=projectMemberService
    }
    */

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getProjectMembers(@PathVariable Long projectId) {
        Long userId=1L;
        return ResponseEntity.ok(projectMemberService.getProjectMembers(projectId, userId));
    }

    @PostMapping
    public  ResponseEntity<MemberResponse> inviteMember(
            @PathVariable Long projectId,
            @RequestBody InviteMemberRequest request
    ){
        Long userId=1L;
        return ResponseEntity.status(HttpStatus.CREATED).body(
                projectMemberService.inviteMember(projectId, request, userId));
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<MemberResponse> updateMemberRole(
        @PathVariable Long projectId,
        @PathVariable Long memberId,
        @RequestBody InviteMemberRequest request

    ){
        Long userId=1L;
        return ResponseEntity.ok(projectMemberService.updateMemberRole(projectId,memberId,request,userId));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<MemberResponse> deleteMember(
            @PathVariable Long projectId,
            @PathVariable Long memberId
    ){
        Long userId=1L;
        return ResponseEntity.ok(projectMemberService.deleteProjectMember(projectId, memberId, userId));
    }
}
