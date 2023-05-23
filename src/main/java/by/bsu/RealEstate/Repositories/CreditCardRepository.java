package by.bsu.RealEstate.Repositories;

import by.bsu.RealEstate.Models.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
     List<CreditCard> findCreditCardsByUserId(long id);

}