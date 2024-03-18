package com.zcourses.zcourses.modules.course.repository;

import com.zcourses.zcourses.modules.course.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {
    Optional<CourseEntity> findByName (String name);
    Optional<CourseEntity> findById (UUID id);
}
