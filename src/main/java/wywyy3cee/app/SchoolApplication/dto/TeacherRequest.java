package wywyy3cee.app.SchoolApplication.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeacherRequest {
    private String firstName;
    private String lastName;
    private String middleName;
    private String subject;
}
