package by.bsu.RealEstate.Mappers;

import by.bsu.RealEstate.Models.DTO.UserDTO;
import by.bsu.RealEstate.Models.User;

public class UserMapper {
    public UserMapper() {
    }

    public UserDTO mapUserToUserDTO(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setLogin(user.getLogin());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

    public User mapUserDTOToUser(UserDTO userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        return user;
    }
}
