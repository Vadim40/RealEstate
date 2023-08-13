package by.bsu.RealEstate.Models.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

}
