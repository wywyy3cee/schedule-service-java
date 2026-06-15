package wywyy3cee.app.SchoolApplication.service;

import org.springframework.stereotype.Service;
import wywyy3cee.app.SchoolApplication.model.SchoolClass;
import wywyy3cee.app.SchoolApplication.repository.SchoolClassRepository;

import java.util.List;

@Service
public class SchoolClassService {
    private final SchoolClassRepository schoolClassRepository;

    public SchoolClassService(SchoolClassRepository schoolClassRepository) {
        this.schoolClassRepository = schoolClassRepository;
    }

    public List<SchoolClass> getAllClasses() {
        return schoolClassRepository.findAll();
    }
}
