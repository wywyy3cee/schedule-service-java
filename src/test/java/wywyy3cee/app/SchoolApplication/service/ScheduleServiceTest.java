package wywyy3cee.app.SchoolApplication.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wywyy3cee.app.SchoolApplication.dto.ScheduleItemDto;
import wywyy3cee.app.SchoolApplication.dto.ScheduleRequest;
import wywyy3cee.app.SchoolApplication.exception.ConflictException;
import wywyy3cee.app.SchoolApplication.exception.NotFoundException;
import wywyy3cee.app.SchoolApplication.model.Schedule;
import wywyy3cee.app.SchoolApplication.model.SchoolClass;
import wywyy3cee.app.SchoolApplication.model.Teacher;
import wywyy3cee.app.SchoolApplication.repository.ScheduleRepository;
import wywyy3cee.app.SchoolApplication.repository.SchoolClassRepository;
import wywyy3cee.app.SchoolApplication.repository.TeacherRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private SchoolClassRepository schoolClassRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    private Teacher teacher;
    private SchoolClass schoolClass;
    private Schedule schedule;

    @BeforeEach
    void setUp() {
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("Иван");
        teacher.setLastName("Иванов");
        teacher.setMiddleName("Иванович");

        schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setGroupNumber("9А");

        schedule = new Schedule();
        schedule.setId(1L);
        schedule.setSubject("Математика");
        schedule.setTeacher(teacher);
        schedule.setSchoolClass(schoolClass);
        schedule.setStartTime(LocalTime.of(8, 0));
        schedule.setEndTime(LocalTime.of(8, 45));
        schedule.setDayOfWeek(DayOfWeek.MONDAY);
        schedule.setClassRoom(101);
    }

    @Test
    void getSchedule_returnsLessonsForRequestedDay() {
        LocalDate monday = LocalDate.of(2024, 1, 15);

        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(schoolClass));
        when(scheduleRepository.findByDayOfWeekAndSchoolClass(DayOfWeek.MONDAY, schoolClass))
                .thenReturn(List.of(schedule));

        List<Schedule> result = scheduleService.getSchedule(1L, monday);

        assertThat(result).containsExactly(schedule);
    }

    @Test
    void getSchedule_missingClass_throwsNotFoundException() {
        when(schoolClassRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> scheduleService.getSchedule(99L, LocalDate.of(2024, 1, 15)))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void create_noConflict_savesScheduleAndReturnsDto() {
        ScheduleRequest request = new ScheduleRequest();
        request.setTeacherId(1L);
        request.setSubject("Математика");
        request.setDayOfWeek(DayOfWeek.MONDAY);
        request.setStartTime(LocalTime.of(8, 0));
        request.setEndTime(LocalTime.of(8, 45));
        request.setClassroom(101);

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(schoolClass));
        when(scheduleRepository.existsConflict(1L, DayOfWeek.MONDAY, LocalTime.of(8, 45), LocalTime.of(8, 0)))
                .thenReturn(false);
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);

        ScheduleItemDto dto = scheduleService.create(1L, request);

        assertThat(dto.getSubject()).isEqualTo("Математика");
        assertThat(dto.getTeacherFullName()).isEqualTo("Иван Иванов Иванович");
        assertThat(dto.getClassroom()).isEqualTo(101);
    }

    @Test
    void create_conflict_throwsConflictExceptionAndDoesNotSave() {
        ScheduleRequest request = new ScheduleRequest();
        request.setTeacherId(1L);
        request.setSubject("Физика");
        request.setDayOfWeek(DayOfWeek.MONDAY);
        request.setStartTime(LocalTime.of(8, 0));
        request.setEndTime(LocalTime.of(8, 45));
        request.setClassroom(205);

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(schoolClass));
        when(scheduleRepository.existsConflict(1L, DayOfWeek.MONDAY, LocalTime.of(8, 45), LocalTime.of(8, 0)))
                .thenReturn(true);

        assertThatThrownBy(() -> scheduleService.create(1L, request))
                .isInstanceOf(ConflictException.class);

        verify(scheduleRepository, never()).save(any());
    }

    @Test
    void create_missingTeacher_throwsNotFoundException() {
        ScheduleRequest request = new ScheduleRequest();
        request.setTeacherId(99L);

        when(teacherRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> scheduleService.create(1L, request))
                .isInstanceOf(NotFoundException.class);

        verify(scheduleRepository, never()).save(any());
    }

    @Test
    void create_missingClass_throwsNotFoundException() {
        ScheduleRequest request = new ScheduleRequest();
        request.setTeacherId(1L);

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(schoolClassRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> scheduleService.create(99L, request))
                .isInstanceOf(NotFoundException.class);

        verify(scheduleRepository, never()).save(any());
    }
}