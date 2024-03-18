package com.zcourses.zcourses.modules.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {
    Optional<CourseEntity> findByName (String name);
    Optional<CourseEntity> findById (UUID id);
}
