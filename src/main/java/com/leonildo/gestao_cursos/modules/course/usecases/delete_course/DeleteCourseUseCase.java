package com.leonildo.gestao_cursos.modules.course.usecases.delete_course;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leonildo.gestao_cursos.modules.course.domain.port.CourseRepository;
@Service
public class DeleteCourseUseCase {
    
    @Autowired
    private  CourseRepository courseRepository;
    public void execute(UUID courseId) {
        var course = this.courseRepository.findById(courseId);
        if(!course.isPresent()){
            throw new RuntimeException("Course not found");
        }   
        this.courseRepository.deleteById(courseId);
    }
}
