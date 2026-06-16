package wywyy3cee.app.SchoolApplication.controller;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wywyy3cee.app.SchoolApplication.dto.SchoolDto;
import wywyy3cee.app.SchoolApplication.dto.SchoolRequest;
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

    @PostMapping
    public ResponseEntity<SchoolDto> create(@RequestBody SchoolRequest request) {
        SchoolDto dto = schoolClassService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public SchoolDto update(@PathVariable Long id, @RequestBody SchoolRequest request) {
        return schoolClassService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        schoolClassService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
