package by.bsu.RealEstate.Repositories;

import by.bsu.RealEstate.Models.RealEstate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RealEstateRepository extends JpaRepository<RealEstate, Long> {
    @Query("SELECT r FROM RealEstate r WHERE r.price>=?1 AND r.price<?2")
     Page<RealEstate> findRealEstatesByPrice(int lefPrice, int rightPrice, Pageable pageable);

    @Query("SELECT r FROM RealEstate r WHERE r.countRooms>=?1 AND r.countRooms<=?2")
     Page<RealEstate> findRealEstatesByCountRooms(int leftCountRooms, int rightCountRooms, Pageable pageable);

     Page<RealEstate> findRealEstatesByUserId(long userId, Pageable pageable);


}