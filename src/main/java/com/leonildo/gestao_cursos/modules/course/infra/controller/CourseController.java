package com.leonildo.gestao_cursos.modules.course.infra.controller;



import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leonildo.gestao_cursos.modules.course.domain.entity.CourseEntity;
import com.leonildo.gestao_cursos.modules.course.usecases.create_course.CreateCourseInputDTO;
import com.leonildo.gestao_cursos.modules.course.usecases.create_course.CreateCourseOutputDTO;
import com.leonildo.gestao_cursos.modules.course.usecases.create_course.CreateCourseUseCase;
import com.leonildo.gestao_cursos.modules.course.usecases.delete_course.DeleteCourseUseCase;
import com.leonildo.gestao_cursos.modules.course.usecases.get_course.GetCoursesUseCase;
import com.leonildo.gestao_cursos.modules.course.usecases.update_course.UpdateCourseUseCase;
import com.leonildo.gestao_cursos.modules.course.usecases.update_course.UpdateCursoInputDTO;
import com.leonildo.gestao_cursos.modules.course.usecases.update_course.UpdateStatusCourseUseCase;




@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CreateCourseUseCase createCourseUseCase;

    @Autowired
    private GetCoursesUseCase getCourseUseCase;

    @Autowired
    private UpdateCourseUseCase updateCourseUseCase;

    @Autowired
    private DeleteCourseUseCase deleteCourseUseCase;

    @Autowired
    private UpdateStatusCourseUseCase updateStatusCourseUseCase;


    @PostMapping("/")
    public ResponseEntity<Object> create(@RequestBody CreateCourseInputDTO input ) {
        try {
            CourseEntity course = CourseEntity.toBuilder(input);
            var result  = this.createCourseUseCase.execute(course);
            CreateCourseOutputDTO output = CreateCourseOutputDTO.builder()
                .id(result.getId().toString())
                .name(result.getName())
                .category(result.getCategory())
                .active(result.isActive())
                .createdAt(result.getCreatedAt())
                .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(output);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
       
    }
    @GetMapping("/")
    public ResponseEntity<Object>  getAll(@RequestParam(required = false) String name, @RequestParam(required = false) String category){
        try {
           
            var courses =this.getCourseUseCase.execute(name, category);
            
            List<CreateCourseOutputDTO> output = courses.stream().map(
                course -> CreateCourseOutputDTO.builder()
                    .id(course.getId().toString())
                    .name(course.getName())
                    .category(course.getCategory())
                    .active(course.isActive())
                    .createdAt(course.getCreatedAt())  
                    .build()
            ).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(output);
           
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    } 
    
    @PutMapping("/{id}")
    public ResponseEntity<Object>  update(@PathVariable String id, @RequestBody UpdateCursoInputDTO request ) {
        try {
            var course = this.updateCourseUseCase.execute(UUID.fromString(id), request.name(), request.category());
            CreateCourseOutputDTO output = CreateCourseOutputDTO.builder()
                .id(course.getId().toString())
                .name(course.getName())
                .category(course.getCategory())
                .active(course.isActive())
                .createdAt(course.getCreatedAt())
                .build();
            return ResponseEntity.status(HttpStatus.OK).body(output);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object>  delete(@PathVariable String id) {
        try {
            this.deleteCourseUseCase.execute(UUID.fromString(id));
            return ResponseEntity.status(HttpStatus.OK).body("Course deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object>  updateStatus(@PathVariable String id, @RequestParam boolean status) {
        try {
            var course = this.updateStatusCourseUseCase.execute(UUID.fromString(id), status);
            return ResponseEntity.status(HttpStatus.OK).body(course);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
       
    }
    
}
