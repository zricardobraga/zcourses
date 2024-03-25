package com.zcourses.zcourses.modules.student.repository;

import com.zcourses.zcourses.modules.student.entity.EnrollCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnrollCourseRepository extends JpaRepository<EnrollCourseEntity, UUID> {
}
