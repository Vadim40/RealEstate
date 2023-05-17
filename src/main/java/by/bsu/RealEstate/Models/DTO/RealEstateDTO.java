package by.bsu.RealEstate.Models.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RealEstateDTO {
    @Min(value =1, message = "count of rooms should be more than 0")
    private int countRooms;
    @Min(value =1, message = "price should be more than 0")
    private int price;
    @Min(value =1, message = "square should be more than 0")
    private int square;
    @NotBlank(message = "Type is mandatory")
    @Size(max=15)
    private String type;

    public RealEstateDTO(int countRooms, int price, int square, String type) {
        this.countRooms = countRooms;
        this.price = price;
        this.square = square;
        this.type = type;
    }

    public RealEstateDTO() {
    }

    public int getCountRooms() {
        return countRooms;
    }

    public void setCountRooms(int countRooms) {
        this.countRooms = countRooms;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSquare() {
        return square;
    }

    public void setSquare(int square) {
        this.square = square;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
