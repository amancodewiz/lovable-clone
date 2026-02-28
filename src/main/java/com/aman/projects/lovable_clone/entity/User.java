package com.aman.projects.lovable_clone.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE) //makes everything private no need to write private in front of each
public class User {

    //Using private for the purpose of encapsulation->so that not everyone can change it
     Long id;
     String email;
     String passwordHash;
     String name;

     String avatarUrl;
     Instant createdAt;
     Instant updatedAt;
     Instant deletedAt;
}
