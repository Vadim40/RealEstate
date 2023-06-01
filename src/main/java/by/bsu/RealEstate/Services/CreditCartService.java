package by.bsu.RealEstate.Services;

import by.bsu.RealEstate.Models.CreditCard;
import by.bsu.RealEstate.Repositories.CreditCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCartService {

    private final CreditCardRepository creditCardRepository;

    public CreditCartService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }


    public List<CreditCard> findCreditCards(){
        return creditCardRepository.findAll();
    }
    public List<CreditCard> findCreditCardsByUserId(long id) {
        return creditCardRepository.findCreditCardsByUserId(id);
    }

    public CreditCard findCreditCardById(long id) {
        return creditCardRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void saveCreditCard(CreditCard creditCard) {

        creditCardRepository.save(creditCard);
    }

    public boolean updateCreditCard(long id, CreditCard creditCard) {
        if (creditCardRepository.findById(id).isPresent()) {
            CreditCard creditCardUpdate = findCreditCardById(id);
            creditCardUpdate.setUserId(creditCard.getUserId());
            creditCardUpdate.setCardNumber(creditCard.getCardNumber());
            creditCardUpdate.setDate(creditCard.getDate());
            creditCardUpdate.setCVV(creditCard.getCVV());
            creditCardRepository.save(creditCardUpdate);
            return true;
        }
        return false;
    }

    public boolean deleteCreditCard(long id) {
        if (creditCardRepository.findById(id).isPresent()) {
            creditCardRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
