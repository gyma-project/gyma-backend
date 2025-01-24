//package com.gyma.gyma.service;
//
//import com.gyma.gyma.model.Day;
//import com.gyma.gyma.model.TrainingTime;
//import com.gyma.gyma.model.enums.DayOfTheWeek;
//import com.gyma.gyma.repository.DayRepository;
//import com.gyma.gyma.repository.TrainingTimeRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalTime;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class DayServiceTest {
//
//    @Mock
//    private DayRepository dayRepository;
//
//    @Mock
//    private TrainingTimeRepository trainingTimeRepository;
//
//    @InjectMocks
//    private DayService dayService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testFindDayById() {
//
//        UUID id = UUID.randomUUID();
//        Day mockDay = new Day();
//        mockDay.setId(id);
//        mockDay.setDayOfTheWeek(DayOfTheWeek.MONDAY);
//
//
//        when(dayRepository.findById(id)).thenReturn(Optional.of(mockDay));
//
//
//        Optional<Day> result = dayService.findDayById(id);
//
//
//        assertTrue(result.isPresent());
//        assertEquals(DayOfTheWeek.MONDAY, result.get().getDayOfTheWeek());
//
//
//        verify(dayRepository, times(1)).findById(id);
//    }
//
//    @Test
//    void testSaveDay() {
//
//        Day mockDay = new Day();
//        mockDay.setDayOfTheWeek(DayOfTheWeek.TUESDAY);
//
//
//        when(dayRepository.save(mockDay)).thenReturn(mockDay);
//
//
//        Day result = dayService.saveDay(mockDay);
//
//
//        assertNotNull(result);
//        assertEquals(DayOfTheWeek.TUESDAY, result.getDayOfTheWeek());
//
//
//        verify(dayRepository, times(1)).save(mockDay);
//    }
//
//    @Test
//    void testAddTrainingTimeToDay() {
//
//        UUID dayId = UUID.randomUUID();
//        Day mockDay = new Day();
//        mockDay.setId(dayId);
//
//        TrainingTime trainingTime = new TrainingTime();
//        trainingTime.setStartTime(LocalTime.of(9, 0));
//        trainingTime.setEndTime(LocalTime.of(10, 0));
//
//
//        when(dayRepository.findById(dayId)).thenReturn(Optional.of(mockDay));
//        when(trainingTimeRepository.save(trainingTime)).thenReturn(trainingTime);
//
//
//        boolean result = dayService.addTrainingTimeToDay(dayId, trainingTime);
//
//
//        assertTrue(result);
//
//
//        verify(dayRepository, times(1)).findById(dayId);
//        verify(trainingTimeRepository, times(1)).save(trainingTime);
//    }
//}
