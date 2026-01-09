package com.leonildo.gestao_cursos.modules.course.usecases.create_course;

import lombok.Builder;


@Builder
public record CreateCourseOutputDTO(String id, String name, String category, boolean active, String createdAt) {
    
}
