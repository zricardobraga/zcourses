package com.zcourses.zcourses.exceptions;

public class CourseNotFoundException extends RuntimeException{
    public CourseNotFoundException(){
        super("Course not found");
    }
}
