package by.bsu.RealEstate.Mappers;

import by.bsu.RealEstate.Models.CreditCard;
import by.bsu.RealEstate.Models.DTO.CreditCardDTO;

public class CreditCardMapper {
    public CreditCardMapper() {
    }

    public CreditCardDTO mapCreditCardToCreditCadDTO(CreditCard creditCard) {
        CreditCardDTO creditCardDto = new CreditCardDTO();
        creditCardDto.setCardNumber(creditCard.getCardNumber());
        creditCardDto.setDate(creditCard.getDate());
        creditCardDto.setCVV(creditCard.getCVV());
        return creditCardDto;
    }

    public CreditCard mapCreditCardDtoToCreditCard(CreditCardDTO creditCardDto) {
        CreditCard creditCard = new CreditCard();
        creditCard.setCardNumber(creditCardDto.getCardNumber());
        creditCard.setDate(creditCardDto.getDate());
        creditCard.setCVV(creditCardDto.getCVV());
        return creditCard;
    }
}
