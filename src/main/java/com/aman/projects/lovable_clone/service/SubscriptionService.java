package com.aman.projects.lovable_clone.service;

import com.aman.projects.lovable_clone.dto.subscription.CheckoutRequest;
import com.aman.projects.lovable_clone.dto.subscription.CheckoutResponse;
import com.aman.projects.lovable_clone.dto.subscription.PortalResponse;
import com.aman.projects.lovable_clone.dto.subscription.SubscriptionResponse;
import org.springframework.stereotype.Service;

@Service
public interface SubscriptionService {
    SubscriptionResponse getCurrentSubscription(Long userId);

     CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request, Long userId);

     PortalResponse openCustomerPortal(Long userId);
}
