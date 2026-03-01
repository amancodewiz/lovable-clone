package com.aman.projects.lovable_clone.dto.auth;

public record AuthResponse(
        String token,
        UserProfileResponse user
) {

}

//How to create obj of UserProfileResponse?
//dummy: new AuthResponse("", new UserProfileResponse());