package wywyy3cee.app.SchoolApplication.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wywyy3cee.app.SchoolApplication.dto.response.SchoolResponse;
import wywyy3cee.app.SchoolApplication.dto.request.SchoolRequest;
import wywyy3cee.app.SchoolApplication.exception.NotFoundException;
import wywyy3cee.app.SchoolApplication.model.SchoolClass;
import wywyy3cee.app.SchoolApplication.repository.SchoolClassRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SchoolClassServiceTest {

    @Mock
    private SchoolClassRepository schoolClassRepository;

    @InjectMocks
    private SchoolClassService schoolClassService;

    private SchoolClass schoolClass;

    @BeforeEach
    void setUp() {
        schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setGroupNumber("9А");
    }

    @Test
    void getAllClasses_returnsAllClasses() {
        when(schoolClassRepository.findAll()).thenReturn(List.of(schoolClass));

        List<SchoolClass> result = schoolClassService.getAllClasses();

        assertThat(result).containsExactly(schoolClass);
    }

    @Test
    void create_savesClassAndReturnsDto() {
        SchoolRequest request = new SchoolRequest();
        request.setGroupNumber("9А");

        when(schoolClassRepository.save(any(SchoolClass.class))).thenReturn(schoolClass);

        SchoolResponse dto = schoolClassService.create(request);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getGroupNumber()).isEqualTo("9А");
    }

    @Test
    void update_existingClass_updatesAndReturnsDto() {
        SchoolRequest request = new SchoolRequest();
        request.setGroupNumber("10Б");

        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(schoolClass));
        when(schoolClassRepository.save(any(SchoolClass.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        SchoolResponse dto = schoolClassService.update(1L, request);

        assertThat(dto.getGroupNumber()).isEqualTo("10Б");
    }

    @Test
    void update_missingClass_throwsNotFoundException() {
        when(schoolClassRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> schoolClassService.update(99L, new SchoolRequest()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void delete_existingClass_callsRepositoryDelete() {
        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(schoolClass));

        schoolClassService.delete(1L);

        verify(schoolClassRepository).deleteById(1L);
    }

    @Test
    void delete_missingClass_throwsNotFoundException() {
        when(schoolClassRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> schoolClassService.delete(99L))
                .isInstanceOf(NotFoundException.class);

        verify(schoolClassRepository, never()).deleteById(any());
    }
}