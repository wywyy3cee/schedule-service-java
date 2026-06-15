package wywyy3cee.app.SchoolApplication.service;

import org.springframework.stereotype.Service;
import wywyy3cee.app.SchoolApplication.repository.TeacherRepository;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }
}
