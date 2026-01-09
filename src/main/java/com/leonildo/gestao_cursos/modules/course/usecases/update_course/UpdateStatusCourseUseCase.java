package com.leonildo.gestao_cursos.modules.course.usecases.update_course;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leonildo.gestao_cursos.modules.course.domain.entity.CourseEntity;
import com.leonildo.gestao_cursos.modules.course.domain.port.CourseRepository;

@Service
public class UpdateStatusCourseUseCase {

    @Autowired
    private  CourseRepository courseRepository;

    public CourseEntity execute(UUID courseId, boolean status) {
        var course = this.courseRepository.findById(courseId);
        if(!course.isPresent()){
            throw new RuntimeException("Course not found");
        }
        course.get().setActive(status);
        return this.courseRepository.save(course.get());

    }

    
}
