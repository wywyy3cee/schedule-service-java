package wywyy3cee.app.SchoolApplication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wywyy3cee.app.SchoolApplication.dto.response.SchoolResponse;
import wywyy3cee.app.SchoolApplication.dto.request.SchoolRequest;
import wywyy3cee.app.SchoolApplication.model.SchoolClass;
import wywyy3cee.app.SchoolApplication.service.SchoolClassService;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "SchoolClass", description = "Group class management")
@RestController
@RequestMapping("/api/classes")
public class SchoolClassController {
    private final SchoolClassService schoolClassService;

    public SchoolClassController(SchoolClassService schoolClassService) {
        this.schoolClassService = schoolClassService;
    }

    @Operation(summary = "Get all school group numbers")
    @GetMapping
    public List<SchoolResponse> getAll(){
        List<SchoolClass> classes = schoolClassService.getAllClasses();
        List<SchoolResponse> result = new ArrayList<>();
        for (SchoolClass c : classes) {
            result.add(new SchoolResponse(c.getId(), c.getGroupNumber()));
        }
        return result;
    }

    @Operation(summary = "Add a new school group")
    @PostMapping
    public ResponseEntity<SchoolResponse> create(@RequestBody SchoolRequest request) {
        SchoolResponse dto = schoolClassService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update group number by Path parameter id")
    @PutMapping("/{id}")
    public SchoolResponse update(@PathVariable Long id, @RequestBody SchoolRequest request) {
        return schoolClassService.update(id, request);
    }

    @Operation(summary = "Deleting a group number by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        schoolClassService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
