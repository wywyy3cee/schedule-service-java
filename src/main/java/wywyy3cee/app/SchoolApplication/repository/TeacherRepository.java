package wywyy3cee.app.SchoolApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wywyy3cee.app.SchoolApplication.model.Teacher;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> id(Long id);
}
