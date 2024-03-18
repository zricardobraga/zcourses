package com.zcourses.zcourses.modules.student.useCases;

import com.zcourses.zcourses.exceptions.student.StudentFoundException;
import com.zcourses.zcourses.modules.student.entity.StudentEntity;
import com.zcourses.zcourses.modules.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentUseCase {

    @Autowired
    private StudentRepository studentRepository;

    public StudentEntity create(StudentEntity studentEntity) {
        this.studentRepository.findByEmail(studentEntity.getEmail()).ifPresent(
                (student) -> {throw  new StudentFoundException();}
        );

        return this.studentRepository.save(studentEntity);

    }

    public List<StudentEntity> getAll() {
        return this.studentRepository.findAll();
    }


}
