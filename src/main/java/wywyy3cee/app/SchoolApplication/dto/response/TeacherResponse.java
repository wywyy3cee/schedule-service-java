package wywyy3cee.app.SchoolApplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeacherResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String subject;
}
