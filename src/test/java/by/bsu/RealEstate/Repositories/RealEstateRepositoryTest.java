package by.bsu.RealEstate.Repositories;

import by.bsu.RealEstate.Models.RealEstate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class RealEstateRepositoryTest {
    @Autowired
    private RealEstateRepository realEstateRepository;


    @Test
    public void RealEstateRepository_Save_ReturnSavedRealEstate() {
        RealEstate realEstate = RealEstate.builder().countRooms(2).price(100).square(123)
                .type("house").userId(1L).build();

        RealEstate savedRealEstate = realEstateRepository.save(realEstate);

        Assertions.assertThat(savedRealEstate).isNotNull();
        Assertions.assertThat(savedRealEstate.getId()).isGreaterThan(0);
    }

    @Test
    public void RealEstateRepository_FindAll() {
        RealEstate realEstate = RealEstate.builder().countRooms(2).price(100).square(123)
                .type("house").userId(1L).build();
        RealEstate realEstate1 = RealEstate.builder().countRooms(2).price(100).square(123)
                .type("flat").userId(1L).build();

        realEstateRepository.save(realEstate);
        realEstateRepository.save(realEstate1);
        List<RealEstate> realEstates = realEstateRepository.findAll();

        Assertions.assertThat(realEstates).isNotNull();
        Assertions.assertThat(realEstates.size()).isEqualTo(2);
    }

    @Test
    public void RealEstateRepository_FindById() {
        RealEstate realEstate = RealEstate.builder().countRooms(2).price(100).square(123)
                .type("house").build();

        realEstateRepository.save(realEstate);
        RealEstate returnedRealEstate = realEstateRepository.findById(realEstate.getId()).get();
        Assertions.assertThat(returnedRealEstate).isNotNull();

    }

    @Test
    public void RealEstateRepository_DeleteById() {
        long realEstateId = 1L;
        RealEstate realEstate = RealEstate.builder().countRooms(2).price(100).square(123)
                .type("house").id(realEstateId).build();
        realEstateRepository.save(realEstate);
        realEstateRepository.deleteById(realEstateId);
        Optional<RealEstate> returnedRealEstate = realEstateRepository.findById(realEstateId);
        Assertions.assertThat(returnedRealEstate).isEmpty();

    }

    @Test
    public void findRealEstatesByUserId() {
        long userId = 1L;
        int offset = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(offset, pageSize);
        RealEstate realEstate1 = RealEstate.builder().countRooms(2).price(100).square(123)
                .type("house").userId(1L).build();
        RealEstate realEstate2 = RealEstate.builder().countRooms(2).price(100).square(123)
                .type("house").userId(2L).build();
        realEstateRepository.save(realEstate1);
        realEstateRepository.save(realEstate2);
        Page<RealEstate> realEstatePage = realEstateRepository.findRealEstatesByUserId(userId, pageable);
        Assertions.assertThat(realEstatePage.getTotalElements()).isEqualTo(1);
    }

    @Test
    public void findRealEstatesByPrice() {
        int offset = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(offset, pageSize);
        int leftPrice = 80;
        int rightPrice = 120;
        RealEstate realEstate1 = RealEstate.builder().countRooms(2).price(100).square(123)
                .type("house").userId(1L).build();
        RealEstate realEstate2 = RealEstate.builder().countRooms(2).price(100).square(123)
                .type("house").userId(2L).build();
        RealEstate realEstate3 = RealEstate.builder().countRooms(2).price(200).square(123)
                .type("house").userId(1L).build();
        realEstateRepository.save(realEstate1);
        realEstateRepository.save(realEstate2);
        realEstateRepository.save(realEstate3);
        Page<RealEstate> realEstatePage = realEstateRepository.findRealEstatesByPrice(leftPrice, rightPrice, pageable);
        Assertions.assertThat(realEstatePage.getTotalElements()).isEqualTo(2);
    }

    @Test
    public void findRealEstatesByCountRooms() {
        int offset = 0;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(offset, pageSize);
        int leftCountRooms = 2;
        int rightCountRooms = 3;
        RealEstate realEstate1 = RealEstate.builder().countRooms(2).price(100).square(123)
                .type("house").userId(1L).build();
        RealEstate realEstate2 = RealEstate.builder().countRooms(3).price(100).square(123)
                .type("house").userId(2L).build();
        RealEstate realEstate3 = RealEstate.builder().countRooms(5).price(200).square(123)
                .type("house").userId(1L).build();
        realEstateRepository.save(realEstate1);
        realEstateRepository.save(realEstate2);
        realEstateRepository.save(realEstate3);
        Page<RealEstate> realEstatePage = realEstateRepository
                .findRealEstatesByCountRooms(leftCountRooms, rightCountRooms, pageable);
        Assertions.assertThat(realEstatePage.getTotalElements()).isEqualTo(2);
    }
}