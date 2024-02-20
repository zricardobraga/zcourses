package com.zcourses.zcourses.modules.course.useCases;

import com.zcourses.zcourses.exceptions.CourseFoundException;
import com.zcourses.zcourses.exceptions.CourseNotFoundException;
import com.zcourses.zcourses.modules.course.CourseEntity;
import com.zcourses.zcourses.modules.course.CourseRepository;
import com.zcourses.zcourses.modules.course.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CourseUseCase {

    //injeção de dependência da CourseRepository
    @Autowired
    private CourseRepository courseRepository;

    public CourseEntity create (CourseEntity courseEntity) {
        //procura se já existe um curso com o mesmo nome no sistema, se existir (ifPresent), lança uma exceção, caso contrário, salva o curso no db
        this.courseRepository.findByName(courseEntity.getName()).ifPresent(
                (course) -> {throw new CourseFoundException();}
        );

        return this.courseRepository.save(courseEntity);
    }

    public List<CourseEntity> getAll(){
        return this.courseRepository.findAll();
    }

    public CourseEntity update (UUID id, Map<String, Object> data) {
        //procura, pelo id, no db, se o curso existe, se não existir, ele lança uma exception de curso não encontrado
        CourseEntity course = this.courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException());

        //as ifs abaixo verificam se o map possui as chaves compatíveis com os atributos (name ou category) do banco de dados para realizar
        //a alterção
        if (data.containsKey("name")) {
            course.setName((String) data.get("name"));
        }
        if (data.containsKey("category")){
            course.setCategory((String) data.get("category"));
        }

        //salva o course alterado no db
        return this.courseRepository.save(course);
    }

    public String delete(UUID id) {
        CourseEntity course = this.courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException());

        if(course != null){
            this.courseRepository.deleteById(course.getId());
        }
        return "Deleted course";
    }

    public CourseEntity updateStatus(UUID id, Map<String, Object> data){
        CourseEntity course = this.courseRepository.findById(id).orElseThrow();
        //se no map recebido existir a chave status
        if (data.containsKey("status")) {
            //pega o status do map e faz converte (cast) para uma string
            var statusReceived = (String) data.get("status");
            //converte o status em enum
            StatusEnum statusEnum = StatusEnum.valueOf(statusReceived.toUpperCase());
            //altera o status
            course.setStatus(statusEnum);
        }
        return this.courseRepository.save(course);
    }
}
