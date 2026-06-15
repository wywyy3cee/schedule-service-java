package wywyy3cee.app.SchoolApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeacherDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
}
