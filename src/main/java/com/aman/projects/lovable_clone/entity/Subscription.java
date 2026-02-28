package com.aman.projects.lovable_clone.entity;

import com.aman.projects.lovable_clone.enums.SubscriptionStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE) //makes everything private no need to write private in front of each
public class Subscription {
    Long id;

    User user;
    Plan plan;

    SubscriptionStatus status; // it is an enum

    String stripeCustomerId;
    String stripeSubscriptionId;

    Instant currentPeriodStart;
    Instant currentPeriodEnd;
    Boolean cancelAtPeriodEnd;

    Instant createdAt;//auditing related annotation->when was the subscription createdAt
    Instant updatedAt;//auditing related annotation->when was the subscription updatedAt

}
