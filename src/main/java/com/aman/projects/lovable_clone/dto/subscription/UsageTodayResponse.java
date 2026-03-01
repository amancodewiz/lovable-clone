package com.aman.projects.lovable_clone.dto.subscription;

//Tokens used today
public record UsageTodayResponse(
        int tokensUsed,
        int tokensLimit,
        int previewsRunning,
        int previewsLimit
) {
}
