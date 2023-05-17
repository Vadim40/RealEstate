package by.bsu.RealEstate.Controllers;

import by.bsu.RealEstate.Mappers.RealEstateMapper;
import by.bsu.RealEstate.Mappers.UserMapper;
import by.bsu.RealEstate.Models.CreditCard;
import by.bsu.RealEstate.Models.DTO.CreditCardDTO;
import by.bsu.RealEstate.Models.DTO.RealEstateDTO;
import by.bsu.RealEstate.Models.DTO.UserDTO;
import by.bsu.RealEstate.Models.RealEstate;
import by.bsu.RealEstate.Models.User;
import by.bsu.RealEstate.Services.CreditCartService;
import by.bsu.RealEstate.Services.RealEstateService;
import by.bsu.RealEstate.Services.UserService;
import by.bsu.RealEstate.Mappers.CreditCardMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final RealEstateService realEstateService;
    private final CreditCartService creditCartService;

    public UserController(UserService userService, RealEstateService realEstateService,
                          CreditCartService creditCartService) {
        this.userService = userService;
        this.realEstateService = realEstateService;
        this.creditCartService = creditCartService;
    }

    @GetMapping("/all/{offset}/{pageSize}")
    public ResponseEntity<Page<UserDTO>> getAll(@PathVariable int offset, @PathVariable int pageSize) {
        if (!userService.findAll().isEmpty()) {
            UserMapper userMapper = new UserMapper();
            Page<User> users = userService.findAllUsersWithPagination(offset, pageSize);
            Page<UserDTO> userDTOS = users.map(new Function<User, UserDTO>() {
                @Override
                public UserDTO apply(User user) {
                    return userMapper.mapUserToUserDTO(user);
                }
            });
            return new ResponseEntity<>(userDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getOne(@PathVariable long id) {
        UserMapper userMapper = new UserMapper();
        User user = userService.findUserById(id);
        UserDTO userDTO = userMapper.mapUserToUserDTO(user);
        return new ResponseEntity<>(userDTO, user.getId() != 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/new")
    public ResponseEntity createUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            UserMapper userMapper = new UserMapper();
            userService.saveUser(userMapper.mapUserDTOToUser(userDTO));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity editUser(@PathVariable long id, @RequestBody @Valid UserDTO userDTO,
                                   BindingResult bindingResult) {
        UserMapper userMapper = new UserMapper();
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if( userService.updateUser(id, userMapper.mapUserDTOToUser(userDTO))){
        return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity deleteUser(@PathVariable long id) {
        if (userService.deleteUser(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}/real-estates")
    public ResponseEntity<List<RealEstateDTO>> getRealEstatesByUserId(@PathVariable long id) {
        if (!userService.getRealEstateByUserId(id).isEmpty()) {
            List<RealEstateDTO> realEstateDTOS = new ArrayList<>();
            RealEstateMapper realEstateMapper = new RealEstateMapper();
            for (RealEstate realEstate : userService.getRealEstateByUserId(id)) {
                realEstateDTOS.add(realEstateMapper.mapRealEstateToRealEstateDTO(realEstate));
            }
            return new ResponseEntity<>(realEstateDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/new-real-estate")
    public ResponseEntity createRealEstate(@PathVariable long id, @RequestBody @Valid RealEstateDTO realEstateDTO,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            RealEstateMapper realEstateMapper = new RealEstateMapper();
            RealEstate realEstate = realEstateMapper.mapRealEstateDTOToRealEstate(realEstateDTO);
            realEstate.setUserId(id);
            realEstateService.saveRealEstate(realEstate);
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/new-card")
    public ResponseEntity createCard(@PathVariable long id, @RequestBody @Valid CreditCardDTO creditCardDTO,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            CreditCardMapper creditCardMapper = new CreditCardMapper();
            CreditCard creditCard = new CreditCard();
            creditCard = creditCardMapper.mapCreditCardDtoToCreditCard(creditCardDTO);
            creditCard.setUserId(id);
            creditCartService.saveCreditCard(creditCard);
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}/cards")
    public ResponseEntity<List<CreditCardDTO>> getCreditCardsByUserId(@PathVariable long id) {
        if (!creditCartService.findCreditCardsByUserId(id).isEmpty()) {
            List<CreditCardDTO> creditCardDTOS = new ArrayList<>();
            CreditCardMapper creditCardMapper = new CreditCardMapper();
            for (CreditCard creditCard : creditCartService.findCreditCardsByUserId(id)) {
                creditCardDTOS.add(creditCardMapper.mapCreditCardToCreditCadDTO(creditCard));
            }
            return new ResponseEntity<>(creditCardDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
