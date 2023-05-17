package by.bsu.RealEstate.Repositories;

import by.bsu.RealEstate.Models.RealEstate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface RealEstateRepository extends JpaRepository<RealEstate, Long> {
    @Query("SELECT r FROM RealEstate r WHERE r.price>=?1 AND r.price<?2")
    public Page<RealEstate> findByPrice(int lefPrice, int rightPrice, Pageable pageable);

    @Query("SELECT r FROM RealEstate r WHERE r.countRooms>=?1 AND r.countRooms<=?2")
    public Page<RealEstate> findByCountRooms(int leftCountRooms, int rightCountRooms, Pageable pageable);

    public ArrayList<RealEstate> getRealEstateByUserId(long userId);
}