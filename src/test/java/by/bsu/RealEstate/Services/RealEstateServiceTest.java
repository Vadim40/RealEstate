package by.bsu.RealEstate.Services;

import by.bsu.RealEstate.Models.RealEstate;
import by.bsu.RealEstate.Repositories.RealEstateRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RealEstateServiceTest {
    @Mock
    private RealEstateRepository realEstateRepository;

    @InjectMocks
    private RealEstateService realEstateService;

    @Test
    void findRealEstatesWithPrice() {
        int offset = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(offset, pageSize);
        int leftPrice = 80;
        int rightPrice = 120;
        realEstateService.findRealEstatesWithPrice(leftPrice, rightPrice, offset, pageSize);
        verify(realEstateRepository).findRealEstatesByPrice(leftPrice, rightPrice, pageable);
    }

    @Test
    void findRealEstatesWithCountRooms() {
        int offset = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(offset, pageSize);
        int leftCountRooms = 2;
        int rightCountRooms = 3;
        realEstateService.findRealEstatesWithCountRooms(leftCountRooms, rightCountRooms, offset, pageSize);
        verify(realEstateRepository).findRealEstatesByCountRooms(leftCountRooms, rightCountRooms, pageable);
    }

    @Test
    void findRealEstatesWithPagination() {
        int offset = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(offset, pageSize);
        realEstateService.findRealEstatesWithPagination(offset, pageSize);
        verify(realEstateRepository).findAll(pageable);
    }

    @Test
    void findRealEstatesWithPaginationAndSorting() {
        int offset = 0;
        int pageSize = 2;
        String field = "type";
        realEstateService.findRealEstatesWithPaginationAndSorting(offset, pageSize, field);
        verify(realEstateRepository).findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
    }

    @Test
    void findRealEstatesByUserId() {
        long userId = 1L;
        int offset = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(offset, pageSize);
        RealEstate realEstate1 = RealEstate.builder().countRooms(2).price(100).square(123)
                .type("house").userId(1L).build();
        RealEstate realEstate2 = RealEstate.builder().countRooms(2).price(100).square(123)
                .type("house").userId(1L).build();
        List<RealEstate> realEstates = new ArrayList<>();
        realEstates.add(realEstate2);
        realEstates.add(realEstate1);
        Page<RealEstate> realEstatePage = new PageImpl<>(realEstates);

        when(realEstateRepository.findRealEstatesByUserId(userId, pageable)).thenReturn(realEstatePage);
        Page<RealEstate> returnedPage = realEstateRepository.findRealEstatesByUserId(userId, pageable);

        Assertions.assertThat(returnedPage).isEqualTo(realEstatePage);
        Assertions.assertThat(returnedPage.getContent()).isEqualTo(realEstates);
    }

    @Test
    void findRealEstateByI() {
        long realEstateId = 1L;
        RealEstate realEstate = RealEstate.builder().countRooms(2).price(100).square(123)
                .type("house").id(realEstateId).build();
        when(realEstateRepository.findById(realEstateId)).thenReturn(Optional.of(realEstate));
        realEstateService.findRealEstateById(realEstateId);
        verify(realEstateRepository).findById(realEstateId);
    }

    @Test
    void updateRealEstate() {
        long realEstateId = 1L;
        RealEstate realEstate = RealEstate.builder().countRooms(2).price(100).square(123)
                .type("house").id(realEstateId).build();
        when(realEstateRepository.findById(realEstateId)).thenReturn(Optional.of(realEstate));
        when(realEstateRepository.save(realEstate)).thenReturn(realEstate);

        RealEstate updateRealEstate = realEstateService.updateRealEstate(realEstateId, realEstate);

        Assertions.assertThat(updateRealEstate).isNotNull();
    }


    @Test
    void deleteRealEstate() {
        long realEstateId = 1;
        RealEstate realEstate = RealEstate.builder().countRooms(2).price(100).square(123)
                .type("house").id(realEstateId).build();
        when(realEstateRepository.findById(realEstateId)).thenReturn(Optional.of(realEstate));
        realEstateService.deleteRealEstate(realEstateId);
        verify(realEstateRepository).findById(realEstateId);
        verify(realEstateRepository).delete(realEstate);

    }
}