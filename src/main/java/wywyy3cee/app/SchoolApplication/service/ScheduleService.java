package wywyy3cee.app.SchoolApplication.service;

import org.springframework.stereotype.Service;
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
import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final TeacherRepository teacherRepository;

    public ScheduleService(ScheduleRepository scheduleRepository,
                           SchoolClassRepository schoolClassRepositor   y,
                           TeacherRepository teacherRepository){
        this.scheduleRepository = scheduleRepository;
        this.schoolClassRepository = schoolClassRepository;
        this.teacherRepository = teacherRepository;
    }

    public List<Schedule> getSchedule(Long classId, LocalDate date) {
        SchoolClass schoolClass = schoolClassRepository.findById(classId).orElseThrow(() ->
                new NotFoundException("Class not found"));
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return scheduleRepository.findByDayOfWeekAndSchoolClass(dayOfWeek, schoolClass);
    }

    public ScheduleItemDto create(Long classId, ScheduleRequest request) {
        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(()-> new NotFoundException("Teacher not found"));
        SchoolClass schoolClass = schoolClassRepository.findById(classId)
                .orElseThrow(() -> new NotFoundException("School Class not found"));
        boolean hasConflict = scheduleRepository.existsConflict(
                request.getTeacherId(),
                request.getDayOfWeek(),
                request.getEndTime(),
                request.getStartTime()
        );
        if (hasConflict) {
            throw new ConflictException("This resource is busy.");
        }
        Schedule schedule = new Schedule();
        schedule.setTeacher(teacher);
        schedule.setSubject(request.getSubject());
        schedule.setClassRoom(request.getClassroom());
        schedule.setStartTime(request.getStartTime());
        schedule.setDayOfWeek(request.getDayOfWeek());
        schedule.setSchoolClass(schoolClass);
        schedule.setEndTime(request.getEndTime());
        Schedule saved = scheduleRepository.save(schedule);
        return new ScheduleItemDto(saved.getId(),
                saved.getSubject(),
                saved.getTeacher().getFirstName() + " " +
                        saved.getTeacher().getLastName() +  " " +
                        saved.getTeacher().getMiddleName(),
                saved.getStartTime(), saved.getEndTime(),
                saved.getClassRoom());
    }
}