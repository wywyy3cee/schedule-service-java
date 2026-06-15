package wywyy3cee.app.SchoolApplication.service;

import org.springframework.stereotype.Service;
import wywyy3cee.app.SchoolApplication.model.Schedule;
import wywyy3cee.app.SchoolApplication.model.SchoolClass;
import wywyy3cee.app.SchoolApplication.repository.ScheduleRepository;
import wywyy3cee.app.SchoolApplication.repository.SchoolClassRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final SchoolClassRepository schoolClassRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, SchoolClassRepository schoolClassRepository){
        this.scheduleRepository = scheduleRepository;
        this.schoolClassRepository = schoolClassRepository;
    }

    public List<Schedule> getSchedule(Long classId, LocalDate date) {
        SchoolClass schoolClass = schoolClassRepository.findById(classId).orElseThrow(() ->
                new RuntimeException("Class not found"));
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return scheduleRepository.findByDayOfWeekAndSchoolClass(dayOfWeek, schoolClass);
    }
}