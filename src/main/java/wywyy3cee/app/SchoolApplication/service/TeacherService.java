package wywyy3cee.app.SchoolApplication.service;

import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import wywyy3cee.app.SchoolApplication.dto.TeacherDto;
import wywyy3cee.app.SchoolApplication.dto.TeacherRequest;
import wywyy3cee.app.SchoolApplication.exception.NotFoundException;
import wywyy3cee.app.SchoolApplication.model.Teacher;
import wywyy3cee.app.SchoolApplication.repository.TeacherRepository;

import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getAllTeachers(String subject) {
        if (subject != null && !subject.isBlank()) {
            return teacherRepository.findBySubjectContainingIgnoreCase(subject);
        }
        return teacherRepository.findAll();
    }

    public TeacherDto create(TeacherRequest request) {
        Teacher teacher = new Teacher();
        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setMiddleName(request.getMiddleName());
        teacher.setSubject(request.getSubject());
        Teacher saved = teacherRepository.save(teacher);
        return new TeacherDto(saved.getId(),saved.getFirstName(),
                saved.getLastName(), saved.getMiddleName(), saved.getSubject());
    }

    public TeacherDto update(Long id, TeacherRequest request) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher not found"));
        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setMiddleName(request.getMiddleName());
        teacher.setSubject(request.getSubject());
        Teacher saved = teacherRepository.save(teacher);

        return new TeacherDto(saved.getId(), saved.getFirstName(),
                saved.getLastName(), saved.getMiddleName(), saved.getSubject());
    }

    public void delete(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Id of teacher is not found"));
        teacherRepository.deleteById(id);
    }
}
