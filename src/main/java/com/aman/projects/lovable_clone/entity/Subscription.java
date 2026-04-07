package com.aman.projects.lovable_clone.entity;

import com.aman.projects.lovable_clone.enums.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE) //makes everything private no need to write private in front of each
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subscription {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "plan_id")
    Plan plan;

    @Enumerated(value = EnumType.STRING)
    SubscriptionStatus status; // it is an enum

    String stripeCustomerId;
    String stripeSubscriptionId;

    Instant currentPeriodStart;
    Instant currentPeriodEnd;
    Boolean cancelAtPeriodEnd;

    @CreationTimestamp
    Instant createdAt;//auditing related annotation->when was the subscription createdAt
    @UpdateTimestamp
    Instant updatedAt;//auditing related annotation->when was the subscription updatedAt

}
