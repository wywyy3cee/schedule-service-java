package wywyy3cee.app.SchoolApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wywyy3cee.app.SchoolApplication.model.Schedule;
import wywyy3cee.app.SchoolApplication.model.SchoolClass;

import java.time.DayOfWeek;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByDayOfWeekAndSchoolClass(DayOfWeek dayOfWeek, SchoolClass schoolClass);
}
