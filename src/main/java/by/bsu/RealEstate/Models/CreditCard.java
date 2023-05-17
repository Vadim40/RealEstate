package by.bsu.RealEstate.Models;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "credit_cards")
@SQLDelete(sql = "UPDATE credit_cards SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class CreditCard {
    @Id
    @SequenceGenerator(
            name = "credit_card_sequence",
            sequenceName = "credit_card_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "credit_card_sequence"
    )
    @Column(name = "id", nullable = false)
    private long id;


    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "date")
    private String date;

    @Column(name = "cvv")
    private String CVV;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "deleted")
    private boolean deleted = Boolean.FALSE;


    public CreditCard() {
    }

    public CreditCard(long id, String cardNumber, String date, String CVV, long userId) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.date = date;
        this.CVV = CVV;
        this.userId = userId;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
