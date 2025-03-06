import com.gyma.gyma.repository.TrainingRecordRepository;
import com.gyma.gyma.model.TrainingRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.gyma.gyma.service.TrainingRecordService;
import com.gyma.gyma.exception.ResourceNotFoundException;



public class TrainingRecordServiceRegressionTest {

    @Mock
    private TrainingRecordRepository trainingRecordRepository;

    @InjectMocks
    private TrainingRecordService trainingRecordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeletar() {
        // Dado
        Integer id = 1;
        TrainingRecord trainingRecord = mock(TrainingRecord.class);
        when(trainingRecordRepository.findById(id)).thenReturn(Optional.of(trainingRecord));

        // Chamada ao método
        trainingRecordService.deletar(id);

        // Verificação
        verify(trainingRecordRepository).deleteById(id);
    }

    @Test
    void testDeletarRegistroNaoEncontrado() {
        // Dado
        Integer id = 1;
        when(trainingRecordRepository.findById(id)).thenReturn(Optional.empty());

        // Chamada ao método
        assertThrows(ResourceNotFoundException.class, () -> {
            trainingRecordService.deletar(id);
        });
    }
}
