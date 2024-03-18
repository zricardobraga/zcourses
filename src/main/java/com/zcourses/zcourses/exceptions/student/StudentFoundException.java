package com.zcourses.zcourses.exceptions.student;

public class StudentFoundException extends RuntimeException {
    public StudentFoundException(){
        super("Student previously registered in the system");
    }
}
