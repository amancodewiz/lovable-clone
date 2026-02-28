package com.aman.projects.lovable_clone.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE) //makes everything private no need to write private in front of each
public class Plan {

    Long id;
    String name;
    String stripePriceId;
    Integer maxProjects;
    Integer maxTokensPerDay;
    Integer maxPreviews; //max number of previews allowed per plan
    Boolean unlimitedAi; //unlimited access to LLM, ignore maxTokensPerDay if true;

    Boolean active;
}
