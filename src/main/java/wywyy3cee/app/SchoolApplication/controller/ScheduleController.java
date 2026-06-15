package wywyy3cee.app.SchoolApplication.controller;

import org.springframework.web.bind.annotation.*;
import wywyy3cee.app.SchoolApplication.dto.ScheduleItemDto;
import wywyy3cee.app.SchoolApplication.model.Schedule;
import wywyy3cee.app.SchoolApplication.service.ScheduleService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/classes/{id}/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public List<ScheduleItemDto> getScheduleById(@PathVariable Long id, @RequestParam LocalDate date){
        List<Schedule> scheduleList = scheduleService.getSchedule(id, date);
        List<ScheduleItemDto> result = new ArrayList<>();
        for (Schedule s: scheduleList) {
            result.add(new ScheduleItemDto(s.getId(), s.getSubject(),
                    s.getTeacher().getFirstName() + " " +
                                  s.getTeacher().getLastName() + " " +
                                  s.getTeacher().getMiddleName(), s.getStartTime(),
                                  s.getEndTime(),s.getClassRoom()));

        }
        return result;
    }

}
