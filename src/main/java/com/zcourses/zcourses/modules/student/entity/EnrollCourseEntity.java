package com.zcourses.zcourses.modules.student.entity;


import com.zcourses.zcourses.modules.course.entity.CourseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name= "enrolled_courses")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrollCourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name= "student_id", insertable = false, updatable = false)
    private StudentEntity studentEntity;

    @ManyToOne
    @JoinColumn(name= "course_id", insertable = false, updatable = false)
    private CourseEntity courseEntity;

    @Column(name = "student_id")
    private UUID studentId;

    @Column(name = "course_id")
    private UUID courseId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
