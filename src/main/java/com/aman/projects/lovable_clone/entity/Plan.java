package com.aman.projects.lovable_clone.entity;

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
