package wywyy3cee.app.SchoolApplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wywyy3cee.app.SchoolApplication.dto.TeacherDto;
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

}
