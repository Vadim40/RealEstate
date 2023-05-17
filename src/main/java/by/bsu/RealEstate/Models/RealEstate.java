package by.bsu.RealEstate.Models;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "real_estates")
@SQLDelete(sql = "UPDATE real_estates SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class RealEstate {
    @Id
    @SequenceGenerator(
            name = "real_estate_sequence",
            sequenceName = "real_estate_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "real_estate_sequence"
    )
    private long id;
    @Column(name = "type")
    private String type;
    @Column(name = "price")
    private Integer price;
    @Column(name = "square")
    private Integer square;
    @Column(name = "count_rooms")

    private Integer countRooms;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "deleted")
    private boolean deleted = Boolean.FALSE;

    public RealEstate() {
    }

    public RealEstate(long id, String type, Integer price, Integer square, Integer countRooms, boolean deleted) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.square = square;
        this.countRooms = countRooms;
        this.deleted = deleted;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getSquare() {
        return square;
    }

    public void setSquare(Integer square) {
        this.square = square;
    }

    public Integer getCountRooms() {
        return countRooms;
    }

    public void setCountRooms(Integer countRooms) {
        this.countRooms = countRooms;
    }


    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "RealEstate{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", square=" + square +
                ", countRooms=" + countRooms +
                '}';
    }
}