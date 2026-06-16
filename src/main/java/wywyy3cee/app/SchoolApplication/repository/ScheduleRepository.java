package wywyy3cee.app.SchoolApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wywyy3cee.app.SchoolApplication.model.Schedule;
import wywyy3cee.app.SchoolApplication.model.SchoolClass;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByDayOfWeekAndSchoolClass(DayOfWeek dayOfWeek, SchoolClass schoolClass);

    @Query("SELECT COUNT(s) > 0 FROM Schedule s " +
            "WHERE s.teacher.id = :teacherId " +
            "AND s.dayOfWeek = :dayOfWeek " +
            "AND s.startTime < :endTime " +
            "AND s.endTime > :startTime")
    boolean existsConflict(@Param("teacherId") Long teacherId,
                           @Param("dayOfWeek") DayOfWeek dayOfWeak,
                           @Param("endTime")   LocalTime endTime,
                           @Param("startTime") LocalTime startTime);
}
