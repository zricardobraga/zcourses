package com.zcourses.zcourses.exceptions;

public class CourseFoundException extends RuntimeException {
    public CourseFoundException() {
        super("Course previously registered in the system");
    }
}
