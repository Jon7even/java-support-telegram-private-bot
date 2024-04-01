package jon7even.repository;

import com.github.jon7even.dto.gift.GiftBuildingDto;
import com.github.jon7even.mapper.GiftMapper;
import com.github.jon7even.model.gift.GiftEntity;
import com.github.jon7even.model.gift.GiftStatus;
import com.github.jon7even.repository.GiftRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GiftRepositoryTest extends GenericRepositoryTest {
    @Autowired
    private GiftRepository giftRepository;

    private GiftBuildingDto giftBuildDtoOne;
    private GiftBuildingDto giftBuildDtoSecond;
    private GiftBuildingDto giftBuildDtoThird;

    private GiftEntity giftInBaseOne;
    private GiftEntity giftInBaseSecond;
    private GiftEntity giftInBaseThird;

    @AfterEach
    void clearRepository() {
        giftRepository.deleteAll();
    }

    @BeforeEach
    void addGift() {
        giftBuildDtoOne = GiftBuildingDto.builder().name("GiftOneACTIV").status(GiftStatus.ACTIVATED).build();
        giftBuildDtoSecond = GiftBuildingDto.builder().name("GiftSecondACTIV").status(GiftStatus.ACTIVATED).build();
        giftBuildDtoThird = GiftBuildingDto.builder().name("GiftThirdDEACTIV").status(GiftStatus.DEACTIVATED).build();

        GiftEntity GiftEntityForSaveOne = GiftMapper.INSTANCE.toEntityFromBuildingDto(
                giftBuildDtoOne, userInBaseOne
        );
        GiftEntity GiftEntityForSaveSecond = GiftMapper.INSTANCE.toEntityFromBuildingDto(
                giftBuildDtoSecond, userInBaseSecond
        );
        GiftEntity GiftEntityForSaveThird = GiftMapper.INSTANCE.toEntityFromBuildingDto(
                giftBuildDtoThird, userInBaseThird
        );

        giftInBaseOne = giftRepository.save(GiftEntityForSaveOne);
        giftInBaseSecond = giftRepository.save(GiftEntityForSaveSecond);
        giftInBaseThird = giftRepository.save(GiftEntityForSaveThird);
    }

    @Test
    @DisplayName("Корректное сохранение сущности подарка")
    void saveCorrectGiftEntity() {
        assertNotNull(giftInBaseOne);
        assertEquals(firstId, giftInBaseOne.getId());
        assertEquals(giftBuildDtoOne.getName(), giftInBaseOne.getName());
        assertEquals(userInBaseOne, giftInBaseOne.getCreator());
        assertEquals(giftBuildDtoOne.getStatus(), giftInBaseOne.getStatus());

        assertNotNull(giftInBaseSecond);
        assertEquals(secondId, giftInBaseSecond.getId());
        assertEquals(giftBuildDtoSecond.getName(), giftInBaseSecond.getName());
        assertEquals(userInBaseSecond, giftInBaseSecond.getCreator());
        assertEquals(giftBuildDtoSecond.getStatus(), giftInBaseSecond.getStatus());

        assertNotNull(giftInBaseThird);
        assertEquals(thirdId, giftInBaseThird.getId());
        assertEquals(giftBuildDtoThird.getName(), giftInBaseThird.getName());
        assertEquals(userInBaseThird, giftInBaseThird.getCreator());
        assertEquals(giftBuildDtoThird.getStatus(), giftInBaseThird.getStatus());

        assertEquals(giftRepository.findAll().size(), 3);
    }
}
