package wywyy3cee.app.SchoolApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class ScheduleItemDto {
    private Long id;
    private String subject;
    private String teacherFullName;
    private LocalTime startTime;
    private LocalTime endTime;
    private int classroom;
}
