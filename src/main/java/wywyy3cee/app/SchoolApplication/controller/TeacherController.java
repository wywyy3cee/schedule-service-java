package wywyy3cee.app.SchoolApplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wywyy3cee.app.SchoolApplication.dto.TeacherDto;
import wywyy3cee.app.SchoolApplication.dto.TeacherRequest;
import wywyy3cee.app.SchoolApplication.model.Teacher;
import wywyy3cee.app.SchoolApplication.service.TeacherService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<TeacherDto> getAll() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        List<TeacherDto> result = new ArrayList<>();
        for (Teacher t: teachers) {
            result.add(new TeacherDto(t.getId(), t.getFirstName(),
                    t.getLastName(), t.getMiddleName()));
        }
        return result;
    }

    @PostMapping
    public ResponseEntity<TeacherDto> create(@RequestBody TeacherRequest request) {
        TeacherDto dto = teacherService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public TeacherDto update(@PathVariable Long id, @RequestBody TeacherRequest request) {
        return teacherService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        teacherService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
