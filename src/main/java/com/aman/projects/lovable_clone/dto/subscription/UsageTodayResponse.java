package com.aman.projects.lovable_clone.dto.subscription;

//Tokens used today
public record UsageTodayResponse(
        Integer tokensUsed,
        Integer tokensLimit,
        Integer previewsRunning,
        Integer previewsLimit
) {
}
