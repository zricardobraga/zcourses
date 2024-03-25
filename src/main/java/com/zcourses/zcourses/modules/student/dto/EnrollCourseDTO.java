package com.zcourses.zcourses.modules.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollCourseDTO {
    private UUID idStudent;
    private UUID idCourse;
}
