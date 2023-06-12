package by.bsu.RealEstate.Models.DTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardDTO {
    @NotBlank
    @Pattern(regexp = "[0-9]{16}")
    private String cardNumber;

    @NotBlank
    @Pattern(regexp = "[0-9]{3}")
    private String CVV;

    @NotBlank
    @Pattern(regexp = "^(0[1-9]|1[0-2])/(\\d{2})$")
    private String date;



}