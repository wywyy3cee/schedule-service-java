package wywyy3cee.app.SchoolApplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wywyy3cee.app.SchoolApplication.dto.SchoolDto;
import wywyy3cee.app.SchoolApplication.model.SchoolClass;
import wywyy3cee.app.SchoolApplication.service.SchoolClassService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class SchoolClassController {
    private final SchoolClassService schoolClassService;

    public SchoolClassController(SchoolClassService schoolClassService) {
        this.schoolClassService = schoolClassService;
    }

    @GetMapping
    public List<SchoolDto> getAll(){
        List<SchoolClass> classes = schoolClassService.getAllClasses();
        List<SchoolDto> result = new ArrayList<>();
        for (SchoolClass c : classes) {
            result.add(new SchoolDto(c.getId(), c.getGroupNumber()));
        }
        return result;
    }

}
