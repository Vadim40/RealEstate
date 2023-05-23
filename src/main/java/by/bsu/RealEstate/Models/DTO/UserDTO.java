package by.bsu.RealEstate.Models.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;



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

    public UserDTO() {
    }

    public UserDTO(String firstName, String lastName, String email, String password, String login) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "firstName = " + firstName + ", " +
                "lastName = " + lastName + ", " +
                "email = " + email + ", " +
                "password = " + password + ", " +
                "login = " + login + ")";
    }
}