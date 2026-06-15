package wywyy3cee.app.SchoolApplication.service;

import org.springframework.stereotype.Service;
import wywyy3cee.app.SchoolApplication.model.Teacher;
import wywyy3cee.app.SchoolApplication.repository.TeacherRepository;

import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }
}
