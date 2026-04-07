package com.aman.projects.lovable_clone.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsageLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    User user;
    Project project;
    String action;
    Integer tokensUsed;
    Integer durationMs;

    String metaData; //JSON of {model_used, prompt_used

    Integer createdAt;
}
