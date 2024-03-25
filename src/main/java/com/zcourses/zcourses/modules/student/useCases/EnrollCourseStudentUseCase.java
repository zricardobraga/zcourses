package com.zcourses.zcourses.modules.student.useCases;

import com.zcourses.zcourses.exceptions.course.CourseNotFoundException;
import com.zcourses.zcourses.exceptions.student.StudentNotFoundException;
import com.zcourses.zcourses.modules.course.repository.CourseRepository;
import com.zcourses.zcourses.modules.student.entity.EnrollCourseEntity;
import com.zcourses.zcourses.modules.student.repository.EnrollCourseRepository;
import com.zcourses.zcourses.modules.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EnrollCourseStudentUseCase {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollCourseRepository enrollCourseRepository;

    public EnrollCourseEntity enroll (UUID idStudent, UUID idCourse) {
        //verificar se o candidato existe
        this.studentRepository.findById(idStudent).orElseThrow(() -> new StudentNotFoundException());
        //verificar se o curso existe
        this.courseRepository.findById(idCourse).orElseThrow(() -> new CourseNotFoundException());

        //fazer a matrícula

        var enrollCourse = EnrollCourseEntity.builder()
                .studentId(idStudent)
                .courseId(idCourse)
                .build();

        enrollCourse = enrollCourseRepository.save(enrollCourse);


        return enrollCourse;
    }

}
