package com.zcourses.zcourses.modules.course.controller;

import com.zcourses.zcourses.modules.course.CourseEntity;
import com.zcourses.zcourses.modules.course.StatusEnum;
import com.zcourses.zcourses.modules.course.useCases.CourseUseCase;
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
public class CourseController {

    @Autowired
    private CourseUseCase courseUseCase;

    @PostMapping("/create")
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
    public ResponseEntity<Object> update(@PathVariable UUID id, @Valid @RequestBody Map<String, Object> data){

        if (data.get("name").toString().isEmpty() || data.get("category").toString().isEmpty()) {
            return ResponseEntity.badRequest().body("Name or Category are empty");
        }

        CourseEntity updatedCourse = this.courseUseCase.update(id, data);

        return ResponseEntity.accepted().body(updatedCourse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id){
        var result = this.courseUseCase.delete(id);

        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/update-status/{id}")
    public ResponseEntity<Object> updateStatus(@PathVariable UUID id, @RequestBody Map<String, Object> data){
        try {
            CourseEntity updatedCourse = this.courseUseCase.updateStatus(id, data);
            return ResponseEntity.accepted().body(updatedCourse);
        } catch (Exception e) {
            return ResponseEntity.ofNullable("Course not updated");
        }
    }
}
