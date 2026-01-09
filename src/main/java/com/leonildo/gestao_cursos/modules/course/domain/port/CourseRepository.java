package com.leonildo.gestao_cursos.modules.course.domain.port;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leonildo.gestao_cursos.modules.course.domain.entity.CourseEntity;


public interface CourseRepository  extends JpaRepository<CourseEntity, UUID> {
    Optional<CourseEntity> findByName(String name);
    List<CourseEntity> findByNameContainingIgnoreCaseAndCategoryIgnoreCase(String name, String category);
    List<CourseEntity> findByNameContainingIgnoreCase(String name);
    List<CourseEntity> findByCategoryContainingIgnoreCase(String category);
    
}
