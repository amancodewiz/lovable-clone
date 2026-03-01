package com.aman.projects.lovable_clone.service;

import com.aman.projects.lovable_clone.dto.subscription.PlanLimitsResponse;
import com.aman.projects.lovable_clone.dto.subscription.UsageTodayResponse;
import org.springframework.stereotype.Service;

@Service
public interface UsageService {
    UsageTodayResponse getTodayUsageOfUser(Long userId);

    PlanLimitsResponse getCurrentSubscriptionLimitsOfUser(Long userId);
}
