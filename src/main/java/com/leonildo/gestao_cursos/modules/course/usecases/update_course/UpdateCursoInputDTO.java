package com.leonildo.gestao_cursos.modules.course.usecases.update_course;

import lombok.Builder;

@Builder
public record UpdateCursoInputDTO (String name, String category) {
    
}
