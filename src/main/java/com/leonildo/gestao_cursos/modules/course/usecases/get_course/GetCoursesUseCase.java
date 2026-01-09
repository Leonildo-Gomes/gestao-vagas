package com.leonildo.gestao_cursos.modules.course.usecases.get_course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leonildo.gestao_cursos.modules.course.domain.entity.CourseEntity;
import com.leonildo.gestao_cursos.modules.course.domain.port.CourseRepository;

@Service
public class GetCoursesUseCase {

    @Autowired
    private  CourseRepository courseRepository;

    public List<CourseEntity> execute(String name, String category) {

        if(name != null && category != null ) {
            return courseRepository.findByNameContainingIgnoreCaseAndCategoryIgnoreCase(name, category);      
        }
        if(name != null) {
            return courseRepository.findByNameContainingIgnoreCase(name);
        }
        if(category != null) return courseRepository.findByCategoryContainingIgnoreCase(category);
        return courseRepository.findAll();
        
    }
}
