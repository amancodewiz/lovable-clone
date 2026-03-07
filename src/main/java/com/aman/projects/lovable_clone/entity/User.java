package com.aman.projects.lovable_clone.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE) //makes everything private no need to write private in front of each
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="users")
public class User {

    //Using private for the purpose of encapsulation->so that not everyone can change it
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

     String email;
     String passwordHash;
     String name;

     String avatarUrl;
     Instant createdAt;
     Instant updatedAt;

     Instant deletedAt;//soft delete->if deletedAt is null it means user is not deleted, if deletedAt is not null it means user is deleted and can be found when was the user deleted
}
