package com.zcourses.zcourses.exceptions.course;

public class CourseNotFoundException extends RuntimeException{
    public CourseNotFoundException(){
        super("Course not found");
    }
}
