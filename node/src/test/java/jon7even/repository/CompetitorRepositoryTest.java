package jon7even.repository;

import com.github.jon7even.dto.competitor.CompetitorBuildingDto;
import com.github.jon7even.mapper.CompetitorMapper;
import com.github.jon7even.model.competitor.CompetitorEntity;
import com.github.jon7even.repository.CompetitorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CompetitorRepositoryTest extends GenericRepositoryTest {
    @Autowired
    private CompetitorRepository competitorRepository;

    private CompetitorBuildingDto competitorBuildOne;
    private CompetitorBuildingDto competitorBuildSecond;
    private CompetitorBuildingDto competitorBuildThird;

    private CompetitorEntity competitorInBaseOne;
    private CompetitorEntity competitorInBaseSecond;
    private CompetitorEntity competitorInBaseThird;

    @AfterEach
    void clearRepository() {
        competitorRepository.deleteAll();
    }

    @BeforeEach
    void addCompetitorInRepository() {
        competitorBuildOne = CompetitorBuildingDto.builder().name("CompetitorOne").build();
        competitorBuildSecond = CompetitorBuildingDto.builder().name("CompetitorSecond").build();
        competitorBuildThird = CompetitorBuildingDto.builder().name("CompetitorThird").build();

        CompetitorEntity competitorEntityForSaveOne = CompetitorMapper.INSTANCE.toEntityFromBuildingDto(
                competitorBuildOne, LocalDateTime.now(), userEntityOne
        );
        CompetitorEntity competitorEntityForSaveSecond = CompetitorMapper.INSTANCE.toEntityFromBuildingDto(
                competitorBuildSecond, LocalDateTime.now(), userEntitySecond
        );
        CompetitorEntity competitorEntityForSaveThird = CompetitorMapper.INSTANCE.toEntityFromBuildingDto(
                competitorBuildThird, LocalDateTime.now(), userEntityThird
        );

        competitorInBaseOne = competitorRepository.save(competitorEntityForSaveOne);
        competitorInBaseSecond = competitorRepository.save(competitorEntityForSaveSecond);
        competitorInBaseThird = competitorRepository.save(competitorEntityForSaveThird);
    }

    @Test
    @DisplayName("Корректное сохранение сущности конкурента")
    void saveCorrectCompetitorEntity() {
        assertNotNull(competitorInBaseOne);
        assertEquals(firstId, competitorInBaseOne.getId());
        assertEquals(competitorBuildOne.getName(), competitorInBaseOne.getName());
        assertEquals(userInBaseOne, competitorInBaseOne.getCreator());

        assertNotNull(competitorInBaseSecond);
        assertEquals(secondId, competitorInBaseSecond.getId());
        assertEquals(competitorBuildSecond.getName(), competitorInBaseSecond.getName());
        assertEquals(userInBaseSecond, competitorInBaseSecond.getCreator());

        assertNotNull(competitorInBaseThird);
        assertEquals(thirdId, competitorInBaseThird.getId());
        assertEquals(competitorBuildThird.getName(), competitorInBaseThird.getName());
        assertEquals(userInBaseThird, competitorInBaseThird.getCreator());

        assertEquals(competitorRepository.findAll().size(), 3);
    }
}
