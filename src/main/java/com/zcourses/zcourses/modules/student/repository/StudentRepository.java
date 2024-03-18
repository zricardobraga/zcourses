package com.zcourses.zcourses.modules.student.repository;

import com.zcourses.zcourses.modules.student.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {
    Optional<StudentEntity> findByEmail (String email);
//    Optional<StudentEntity> findByID (UUID id);

}
