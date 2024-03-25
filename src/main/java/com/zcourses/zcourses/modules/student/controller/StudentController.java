package com.zcourses.zcourses.modules.student.controller;

import com.zcourses.zcourses.modules.ENUM.StatusEnum;
import com.zcourses.zcourses.modules.student.dto.EnrollCourseDTO;
import com.zcourses.zcourses.modules.student.entity.StudentEntity;
import com.zcourses.zcourses.modules.student.useCases.EnrollCourseStudentUseCase;
import com.zcourses.zcourses.modules.student.useCases.StudentUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServlet;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/students")
@Tag(name= "Estudante")
public class StudentController {

    @Autowired
    private StudentUseCase studentUseCase;

    @Autowired
    private EnrollCourseStudentUseCase enrollCourseStudentUseCase;

    @PostMapping("/create")
    @Operation(summary = " Cadastro", description = "Essa rota é responsável por cadastrar um estudante no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = StudentEntity.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = "Course not found")
    })
    public ResponseEntity<Object> create (@Valid @RequestBody StudentEntity studentEntity) {
        try {
            studentEntity.setStatus(StatusEnum.ACTIVE);
            var result = this.studentUseCase.create(studentEntity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get")
    @Operation(summary = "Listagem", description = "Essa rota é responsável por listar todas os estudantes cadastrados no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = StudentEntity.class)
                    ))
            }),
            @ApiResponse(responseCode = "204", description = "No content")
    })
    public ResponseEntity<List<StudentEntity>> get(){
        List<StudentEntity> students = new ArrayList<>();
        students = this.studentUseCase.getAll();

        if (students.isEmpty()) {
            //todo verificar se existe uma forma melhor de retornar uma mensagem de lista vazia de estudantes
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(students);
    }

    @PostMapping("/course/enroll")
    @Operation(summary = "Inscrição do estudante para um curso", description = "Essa rota é responsável por possibilitar a matriocula de um estudante em um curso")
    public ResponseEntity<Object> enrollCourse(@RequestBody EnrollCourseDTO enrollCourseDTO){
        try {
            var result = this.enrollCourseStudentUseCase.enroll(enrollCourseDTO.getIdStudent(), enrollCourseDTO.getIdCourse());
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
