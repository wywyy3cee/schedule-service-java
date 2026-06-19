package wywyy3cee.app.SchoolApplication.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wywyy3cee.app.SchoolApplication.dto.TeacherDto;
import wywyy3cee.app.SchoolApplication.dto.TeacherRequest;
import wywyy3cee.app.SchoolApplication.exception.NotFoundException;
import wywyy3cee.app.SchoolApplication.model.Teacher;
import wywyy3cee.app.SchoolApplication.repository.TeacherRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    private Teacher teacher;

    @BeforeEach
    void setUp() {
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("Иван");
        teacher.setLastName("Иванов");
        teacher.setMiddleName("Иванович");
        teacher.setSubject("Математика");
    }

    @Test
    void getAllTeachers_withoutSubject_returnsAllTeachers() {
        when(teacherRepository.findAll()).thenReturn(List.of(teacher));

        List<Teacher> result = teacherService.getAllTeachers(null);

        assertThat(result).containsExactly(teacher);
        verify(teacherRepository, never()).findBySubjectContainingIgnoreCase(any());
    }

    @Test
    void getAllTeachers_withBlankSubject_returnsAllTeachers() {
        when(teacherRepository.findAll()).thenReturn(List.of(teacher));

        List<Teacher> result = teacherService.getAllTeachers("   ");

        assertThat(result).containsExactly(teacher);
    }

    @Test
    void getAllTeachers_withSubject_filtersBySubject() {
        when(teacherRepository.findBySubjectContainingIgnoreCase("мат"))
                .thenReturn(List.of(teacher));

        List<Teacher> result = teacherService.getAllTeachers("мат");

        assertThat(result).containsExactly(teacher);
        verify(teacherRepository, never()).findAll();
    }

    @Test
    void create_savesTeacherAndReturnsDto() {
        TeacherRequest request = new TeacherRequest();
        request.setFirstName("Иван");
        request.setLastName("Иванов");
        request.setMiddleName("Иванович");
        request.setSubject("Математика");

        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

        TeacherDto dto = teacherService.create(request);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getFirstName()).isEqualTo("Иван");
        assertThat(dto.getSubject()).isEqualTo("Математика");
    }

    @Test
    void update_existingTeacher_updatesAndReturnsDto() {
        TeacherRequest request = new TeacherRequest();
        request.setFirstName("Пётр");
        request.setLastName("Петров");
        request.setMiddleName("Петрович");
        request.setSubject("Физика");

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(any(Teacher.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TeacherDto dto = teacherService.update(1L, request);

        assertThat(dto.getFirstName()).isEqualTo("Пётр");
        assertThat(dto.getSubject()).isEqualTo("Физика");
    }

    @Test
    void update_missingTeacher_throwsNotFoundException() {
        when(teacherRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> teacherService.update(99L, new TeacherRequest()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void delete_existingTeacher_callsRepositoryDelete() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        teacherService.delete(1L);

        verify(teacherRepository).deleteById(1L);
    }

    @Test
    void delete_missingTeacher_throwsNotFoundException() {
        when(teacherRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> teacherService.delete(99L))
                .isInstanceOf(NotFoundException.class);

        verify(teacherRepository, never()).deleteById(any());
    }
}