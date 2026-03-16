package com.aman.projects.lovable_clone.mapper;


import com.aman.projects.lovable_clone.dto.auth.SignupRequest;
import com.aman.projects.lovable_clone.dto.auth.UserProfileResponse;
import com.aman.projects.lovable_clone.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(SignupRequest signupRequest);

    UserProfileResponse toUserProfileResponse(User user);
}
