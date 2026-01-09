package com.leonildo.gestao_cursos.shared.exceptions;

public class CourseFoundException  extends RuntimeException {

    public CourseFoundException(){
        super("Course already exists");
    }
    
}
