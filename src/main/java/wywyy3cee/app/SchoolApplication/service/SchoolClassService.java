package wywyy3cee.app.SchoolApplication.service;

import org.springframework.stereotype.Service;
import wywyy3cee.app.SchoolApplication.dto.SchoolDto;
import wywyy3cee.app.SchoolApplication.dto.SchoolRequest;
import wywyy3cee.app.SchoolApplication.exception.NotFoundException;
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

    public SchoolDto create(SchoolRequest request) {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setGroupNumber(request.getGroupNumber());
        SchoolClass saved = schoolClassRepository.save(schoolClass);
        return new SchoolDto(saved.getId(), saved.getGroupNumber());
    }

    public SchoolDto update(Long id, SchoolRequest request) {
        SchoolClass schoolClass = schoolClassRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("School Class not found"));
        schoolClass.setGroupNumber(request.getGroupNumber());
        SchoolClass saved = schoolClassRepository.save(schoolClass);

        return new SchoolDto(saved.getId(), saved.getGroupNumber());
    }

    public void delete(Long id) {
        SchoolClass schoolClass = schoolClassRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Id of class is not found"));
        schoolClassRepository.deleteById(id);
    }
}
