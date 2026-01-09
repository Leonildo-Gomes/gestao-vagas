package com.leonildo.gestao_cursos.modules.course.usecases.update_course;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leonildo.gestao_cursos.modules.course.domain.entity.CourseEntity;
import com.leonildo.gestao_cursos.modules.course.domain.port.CourseRepository;
@Service
public class UpdateCourseUseCase {


    @Autowired
    private  CourseRepository courseRepository;
    public CourseEntity execute(UUID courseId,String name, String category) {
        var course = this.courseRepository.findById(courseId);
        if(!course.isPresent()){
            throw new RuntimeException("Course not found");
           
        }
        if(name!=null){
            course.get().setName(name);

        }
        if(category!=null){
            course.get().setCategory(category);
        }
        return this.courseRepository.save(course.get());
    }
    
}
