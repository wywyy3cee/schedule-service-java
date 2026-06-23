package wywyy3cee.app.SchoolApplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class ScheduleItemResponse {
    private Long id;
    private String subject;
    private String teacherFullName;
    private LocalTime startTime;
    private LocalTime endTime;
    private int classroom;
}
