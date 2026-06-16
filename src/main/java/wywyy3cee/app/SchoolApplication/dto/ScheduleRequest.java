package wywyy3cee.app.SchoolApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class ScheduleRequest {
    private Long teacherId;
    private String subject;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private int classroom;
}
