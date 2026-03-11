package com.aman.projects.lovable_clone.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "projects",
//Order matter while you create an index
        indexes = {
                @Index(name = "idx_projects_updated_at_desc", columnList = "updated_at DESC, deleted_at"),//Here first updated_at will be called
                @Index(name = "idx_projects_deleted_at_updated_at_desc", columnList = "deleted_at , updated_at DESC"), //Here deleted_at will be called first, that's why order matters
                @Index(name = "idx_project_deleted_at", columnList = "deleted_at")
        })
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    //nullable = false: Whenever you create a project name has to be defined
    String name;

    //@ManyToOne->Many Project(class) to one owner


    Boolean isPublic = false;

    @CreationTimestamp
    Instant createdAt;

    @UpdateTimestamp
    Instant updatedAt;
    Instant deletedAt;

}
