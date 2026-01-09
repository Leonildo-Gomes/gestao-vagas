package com.leonildo.gestao_cursos.modules.course.usecases.create_course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leonildo.gestao_cursos.modules.course.domain.entity.CourseEntity;
import com.leonildo.gestao_cursos.modules.course.domain.port.CourseRepository;
import com.leonildo.gestao_cursos.shared.exceptions.CourseFoundException;

@Service
public class CreateCourseUseCase {
    @Autowired
    private  CourseRepository courseRepository;

    public CourseEntity execute( CourseEntity input) {
        var course= this.courseRepository.findByName(input.getName());
        if(course.isPresent()){
            throw new CourseFoundException(); 
        }
        return this.courseRepository.save(input);
    }   
    
}
