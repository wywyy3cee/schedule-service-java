package wywyy3cee.app.SchoolApplication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wywyy3cee.app.SchoolApplication.dto.response.TeacherResponse;
import wywyy3cee.app.SchoolApplication.dto.request.TeacherRequest;
import wywyy3cee.app.SchoolApplication.model.Teacher;
import wywyy3cee.app.SchoolApplication.service.TeacherService;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Teachers", description = "Teacher management")
@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Operation(summary = "Get information about all teachers with optional parameter")
    @GetMapping
    public List<TeacherResponse> getAll(@RequestParam(required = false) String subject) {
        List<Teacher> teachers = teacherService.getAllTeachers(subject);
        List<TeacherResponse> result = new ArrayList<>();
        for (Teacher t: teachers) {
            result.add(new TeacherResponse(t.getId(), t.getFirstName(),
                    t.getLastName(), t.getMiddleName(), t.getSubject()));
        }
        return result;
    }

    @Operation(summary = "Add a new teacher")
    @PostMapping
    public ResponseEntity<TeacherResponse> create(@Valid @RequestBody TeacherRequest request) {
        TeacherResponse dto = teacherService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @Operation(summary = "Update teacher info by Path parameter id")
    @PutMapping("/{id}")
    public TeacherResponse update(@PathVariable Long id, @Valid @RequestBody TeacherRequest request) {
        return teacherService.update(id, request);
    }

    @Operation(summary = "Deleting a teacher by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        teacherService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
