package com.zcourses.zcourses.modules.course.controller;

import com.zcourses.zcourses.modules.course.entity.CourseEntity;
import com.zcourses.zcourses.modules.ENUM.StatusEnum;
import com.zcourses.zcourses.modules.course.useCases.CourseUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
@Tag(name = "Curso")
public class CourseController {

    @Autowired
    private CourseUseCase courseUseCase;

    @PostMapping("/create")
    @Operation(summary = " Cadastro", description = "Essa rota é responsável por cadastrar o curso no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CourseEntity.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = "Course not found")
    })
    public ResponseEntity<Object> create (@Valid @RequestBody CourseEntity courseEntity) {
        try {
            courseEntity.setStatus(StatusEnum.ACTIVE);
            //salva na variável result o curso criado
            var result = this.courseUseCase.create(courseEntity);
            //retorna um código 200 e o curso criado
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            //caso tenha ocorrido algum problema, retorna um código de erro (bad request)
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get")
    @Operation(summary = "Listagem", description = "Essa rota é responsável por listar todas as vagas disponíveis")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema (schema = @Schema(implementation = CourseEntity.class)
                    ))
            }),
            @ApiResponse(responseCode = "204", description = "No content")
    })
    public ResponseEntity<List<CourseEntity>> get(){
        List<CourseEntity> courses = new ArrayList<>();
        courses = this.courseUseCase.getAll();
        if(courses.isEmpty()) {
            //todo verificar se existe uma forma melhor de retornar uma mensagem de lista vazia de cursos
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(courses);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Atualiza as informações do curso", description = "Essa rota é responsável por atualizar as informações do curso, exceto o Status")
    @ApiResponses({
            @ApiResponse(responseCode = "202", content = {
                    @Content(schema = @Schema(implementation = CourseEntity.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = "Course not found")
    })
    public ResponseEntity<Object> update(@PathVariable UUID id, @Valid @RequestBody Map<String, Object> data){

        if (data.get("name").toString().isEmpty() || data.get("category").toString().isEmpty()) {
            return ResponseEntity.badRequest().body("Name or Category are empty");
        }

        CourseEntity updatedCourse = this.courseUseCase.update(id, data);

        return ResponseEntity.accepted().body(updatedCourse);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Deleta o curso", description = "Essa rota é responsável por deletar o curso")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Deleted course"),
            @ApiResponse(responseCode = "400", description = "Course not found")
    })
    public ResponseEntity<Object> delete(@PathVariable UUID id){
        var result = this.courseUseCase.delete(id);

        return ResponseEntity.ok().body("Deleted course");
    }

    @PatchMapping("/update-status/{id}")
    @PutMapping("/update/{id}")
    @Operation(summary = "Atualiza o status do curso", description = "Essa rota é responsável por atualizar o Status do curso")
    @ApiResponses({
            @ApiResponse(responseCode = "202", content = {
                    @Content(schema = @Schema(implementation = CourseEntity.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = "Course not updated")
    })
    public ResponseEntity<Object> updateStatus(@PathVariable UUID id, @RequestBody Map<String, Object> data){
        try {
            CourseEntity updatedCourse = this.courseUseCase.updateStatus(id, data);
            return ResponseEntity.accepted().body(updatedCourse);
        } catch (Exception e) {
            return ResponseEntity.ofNullable("Course not updated");
        }
    }
}
