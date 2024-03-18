package com.zcourses.zcourses.exceptions.student;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(){
        super("Student not found");
    }
}
