package by.bsu.RealEstate.Models.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotBlank(message = "firstName is mandatory")
    private String firstName;
    @NotBlank(message = "lastName is mandatory")
    private String lastName;
    @NotBlank(message = "email is mandatory")
    @Email
    private String email;
    @NotBlank(message = "password is mandatory")
    private String password;
    @NotBlank(message = "login is mandatory")
    private String login;


}