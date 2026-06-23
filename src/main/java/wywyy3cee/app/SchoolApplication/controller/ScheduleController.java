package wywyy3cee.app.SchoolApplication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wywyy3cee.app.SchoolApplication.dto.response.ScheduleItemResponse;
import wywyy3cee.app.SchoolApplication.dto.request.ScheduleRequest;
import wywyy3cee.app.SchoolApplication.model.Schedule;
import wywyy3cee.app.SchoolApplication.service.ScheduleService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Tag(name = "Schedule", description = "Schedule management")
@RestController
@RequestMapping("/api/classes/{id}/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Operation(summary = "Retrieve the schedule by ID via a path parameter.")
    @GetMapping
    public List<ScheduleItemResponse> getScheduleById(@PathVariable Long id, @RequestParam LocalDate date){
        List<Schedule> scheduleList = scheduleService.getSchedule(id, date);
        List<ScheduleItemResponse> result = new ArrayList<>();
        for (Schedule s: scheduleList) {
            result.add(new ScheduleItemResponse(s.getId(), s.getSubject(),
                    s.getTeacher().getFirstName() + " " +
                                  s.getTeacher().getLastName() + " " +
                                  s.getTeacher().getMiddleName(), s.getStartTime(),
                                  s.getEndTime(),s.getClassRoom()));

        }
        return result;
    }

    @Operation(summary = "Add a new schedule")
    @PostMapping
    public ResponseEntity<ScheduleItemResponse> create(@PathVariable Long id,
                                                       @RequestBody ScheduleRequest request) {
        ScheduleItemResponse dto = scheduleService.create(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

}
