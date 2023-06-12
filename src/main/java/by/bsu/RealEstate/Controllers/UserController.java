package by.bsu.RealEstate.Controllers;

import by.bsu.RealEstate.Mappers.UserMapper;
import by.bsu.RealEstate.Models.DTO.UserDTO;
import by.bsu.RealEstate.Models.User;
import by.bsu.RealEstate.Services.CustomUserDetailsService;
import by.bsu.RealEstate.Services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;


    @GetMapping("/all/{offset}/{pageSize}")
    public ResponseEntity<Page<UserDTO>> getAll(@PathVariable int offset, @PathVariable int pageSize) {
        Page<User> users = userService.findUsersWithPagination(offset, pageSize);
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        UserMapper userMapper = new UserMapper();
        Page<UserDTO> userDTOS = users.map(userMapper::mapUserToUserDTO);
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getOne(@PathVariable long id) {
        UserMapper userMapper = new UserMapper();
        User user = userService.findUserById(id);
        UserDTO userDTO = userMapper.mapUserToUserDTO(user);
        return new ResponseEntity<>(userDTO, user.getId() != 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/new")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserMapper userMapper = new UserMapper();
        User user = userMapper.mapUserDTOToUser(userDTO);
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<?> editUser(@PathVariable long id, @RequestBody @Valid UserDTO userDTO,
                                      BindingResult bindingResult) {
        if (!customUserDetailsService.getAuthenticatedUser().getRole().equals("ADMIN")) {
            if (id != customUserDetailsService.getAuthenticatedUser().getId()) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        UserMapper userMapper = new UserMapper();
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (userService.updateUser(id, userMapper.mapUserDTOToUser(userDTO))) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        if (!customUserDetailsService.getAuthenticatedUser().getRole().equals("ADMIN")) {
            if (id != customUserDetailsService.getAuthenticatedUser().getId()) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        if (userService.deleteUser(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
