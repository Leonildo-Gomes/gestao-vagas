package com.leonildo.gestao_cursos.modules.course.domain.entity;

import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.leonildo.gestao_cursos.modules.course.usecases.create_course.CreateCourseInputDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity(name = "courses")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseEntity {
    @Id
    @GeneratedValue( strategy =GenerationType.UUID )
    private UUID id;
    private String name;
    private String category;
    private boolean active;

    @CreationTimestamp
    @Column( name="created_at",updatable = false)
    private String createdAt;
    @CreationTimestamp
    @Column( name="updated_at")
    private String updatedAt;

    public static CourseEntity toBuilder( CreateCourseInputDTO input) {
        return CourseEntity.builder()
                .category(input.category())
                .active(input.active())
                .name(input.name())
                .build();
    }
    
}
