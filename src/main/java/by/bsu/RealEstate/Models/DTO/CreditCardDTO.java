package by.bsu.RealEstate.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public class CreditCardDTO {
    @NotBlank
    @Pattern(regexp = "[0-9]{16}")
    private String cardNumber;
    @NotBlank
    @Pattern(regexp = "0[1-9]|1[0-2]/([0-9]{2})")
    private String date;
    @NotBlank
    @Pattern(regexp = "[0-9]{3}")
    private String CVV;

    public CreditCardDTO() {
    }

    public CreditCardDTO(String cardNumber, String date, String CVV) {
        this.cardNumber = cardNumber;
        this.date = date;
        this.CVV = CVV;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }
}