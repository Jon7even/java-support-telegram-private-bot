package jon7even.repository;

import com.github.jon7even.dto.event.EventBuildingDto;
import com.github.jon7even.mapper.EventMapper;
import com.github.jon7even.entity.event.EventEntity;
import com.github.jon7even.entity.event.EventStatus;
import com.github.jon7even.repository.EventRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EventRepositoryTest extends GenericRepositoryTest {
    @Autowired
    private EventRepository eventRepository;

    private EventBuildingDto eventBuildDtoOne;
    private EventBuildingDto eventBuildDtoSecond;
    private EventBuildingDto eventBuildDtoThird;

    private EventEntity eventInBaseOne;
    private EventEntity eventInBaseSecond;
    private EventEntity eventInBaseThird;

    @AfterEach
    void clearRepository() {
        eventRepository.deleteAll();
    }

    @BeforeEach
    void addEventInRepository() {
        eventBuildDtoOne = EventBuildingDto.builder()
                .name("EventOne")
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusMonths(1))
                .status(EventStatus.ACTIVATED)
                .build();
        eventBuildDtoSecond = EventBuildingDto.builder()
                .name("EventSecond")
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusMonths(2))
                .status(EventStatus.ACTIVATED)
                .build();
        eventBuildDtoThird = EventBuildingDto.builder()
                .name("EventThird")
                .start(LocalDateTime.now().minusHours(1))
                .end(LocalDateTime.now().plusMonths(3))
                .status(EventStatus.DEACTIVATED)
                .build();

        eventInBaseOne = eventRepository.save(
                EventMapper.INSTANCE.toEntityFromBuildingDto(eventBuildDtoOne, userInBaseOne)
        );
        eventInBaseSecond = eventRepository.save(
                EventMapper.INSTANCE.toEntityFromBuildingDto(eventBuildDtoSecond, userInBaseSecond)
        );
        eventInBaseThird = eventRepository.save(
                EventMapper.INSTANCE.toEntityFromBuildingDto(eventBuildDtoThird, userEntityThird)
        );
    }

    @Test
    @DisplayName("Корректное сохранение сущности события")
    void saveCorrectEventEntity() {
        assertNotNull(eventInBaseOne);
        assertEquals(firstId, eventInBaseOne.getId());
        assertEquals(eventBuildDtoOne.getName(), eventInBaseOne.getName());
        assertEquals(eventBuildDtoOne.getStart(), eventInBaseOne.getStart());
        assertEquals(eventBuildDtoOne.getEnd(), eventInBaseOne.getEnd());
        assertEquals(eventBuildDtoOne.getStatus(), eventInBaseOne.getStatus());
        assertEquals(userInBaseOne, eventInBaseOne.getCreator());

        assertNotNull(eventInBaseSecond);
        assertEquals(secondId, eventInBaseSecond.getId());
        assertEquals(eventBuildDtoSecond.getName(), eventInBaseSecond.getName());
        assertEquals(eventBuildDtoSecond.getStart(), eventInBaseSecond.getStart());
        assertEquals(eventBuildDtoSecond.getEnd(), eventInBaseSecond.getEnd());
        assertEquals(eventBuildDtoSecond.getStatus(), eventInBaseSecond.getStatus());
        assertEquals(userInBaseSecond, eventInBaseSecond.getCreator());

        assertNotNull(eventInBaseThird);
        assertEquals(thirdId, eventInBaseThird.getId());
        assertEquals(eventBuildDtoThird.getName(), eventInBaseThird.getName());
        assertEquals(eventBuildDtoThird.getStart(), eventInBaseThird.getStart());
        assertEquals(eventBuildDtoThird.getEnd(), eventInBaseThird.getEnd());
        assertEquals(eventBuildDtoThird.getStatus(), eventInBaseThird.getStatus());
        assertEquals(userInBaseThird, eventInBaseThird.getCreator());

        assertEquals(eventRepository.findAll().size(), 3);
    }
}
