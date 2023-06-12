package by.bsu.RealEstate.Controllers;

import by.bsu.RealEstate.Mappers.CreditCardMapper;
import by.bsu.RealEstate.Models.CreditCard;
import by.bsu.RealEstate.Models.DTO.CreditCardDTO;
import by.bsu.RealEstate.Services.CreditCartService;
import by.bsu.RealEstate.Services.CustomUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/card")
public class CreditCardController {
    private final CreditCartService creditCartService;
    private final CustomUserDetailsService customUserDetailsService;

    public CreditCardController(CreditCartService creditCartService, CustomUserDetailsService customUserDetailsService) {
        this.creditCartService = creditCartService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CreditCardDTO>> getAllCards() {
        List<CreditCard> creditCards=creditCartService.findCreditCards();
        if (creditCards.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        List<CreditCardDTO> creditCardDTOS = new ArrayList<>();
        CreditCardMapper creditCardMapper = new CreditCardMapper();
        for (CreditCard creditCard : creditCards) {
            creditCardDTOS.add(creditCardMapper.mapCreditCardToCreditCadDTO(creditCard));
        }
        return new ResponseEntity<>(creditCardDTOS, HttpStatus.OK);
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<CreditCardDTO>> getCreditCardsByUserId(@PathVariable long userId) {
        if (!customUserDetailsService.getAuthenticatedUser().getRole().equals("ADMIN")) {
            if (userId != customUserDetailsService.getAuthenticatedUser().getId()) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        List<CreditCard> creditCards=creditCartService.findCreditCardsByUserId(userId);

        if (creditCards.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<CreditCardDTO> creditCardDTOS = new ArrayList<>();
        CreditCardMapper creditCardMapper = new CreditCardMapper();
        for (CreditCard creditCard : creditCartService.findCreditCardsByUserId(userId)) {
            creditCardDTOS.add(creditCardMapper.mapCreditCardToCreditCadDTO(creditCard));
        }
        return new ResponseEntity<>(creditCardDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditCardDTO> getCreditCardById(@PathVariable long id) {

        if (!customUserDetailsService.getAuthenticatedUser().getRole().equals("ADMIN")) {
            if (creditCartService.findCreditCardById(id).getUserId() != customUserDetailsService.getAuthenticatedUser().getId()) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        CreditCardMapper creditCardMapper = new CreditCardMapper();
        CreditCardDTO creditCardDTO = creditCardMapper.mapCreditCardToCreditCadDTO(
                creditCartService.findCreditCardById(id));
        return new ResponseEntity<>(creditCardDTO, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<?> createCreditCard(@RequestBody @Valid CreditCardDTO creditCardDTO,
                                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CreditCardMapper creditCardMapper = new CreditCardMapper();
        CreditCard creditCard = creditCardMapper.mapCreditCardDtoToCreditCard(creditCardDTO);
        creditCard.setUserId(customUserDetailsService.getAuthenticatedUser().getId());
        creditCartService.saveCreditCard(creditCard);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<?> editCreditCard(@PathVariable long id, @RequestBody @Valid CreditCardDTO creditCardDTO,
                                            BindingResult bindingResult) {
        if (!customUserDetailsService.getAuthenticatedUser().getRole().equals("ADMIN")) {
            if (creditCartService.findCreditCardById(id).getUserId() != customUserDetailsService.getAuthenticatedUser().getId()) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CreditCardMapper creditCardMapper = new CreditCardMapper();
        CreditCard creditCard= creditCardMapper.mapCreditCardDtoToCreditCard(creditCardDTO);
        creditCard.setUserId(customUserDetailsService.getAuthenticatedUser().getId());
        if (creditCartService.updateCreditCard(id,creditCard )) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteCreditCard(@PathVariable long id) {
        if (!customUserDetailsService.getAuthenticatedUser().getRole().equals("ADMIN")) {
            if (creditCartService.findCreditCardById(id).getUserId() != customUserDetailsService.getAuthenticatedUser().getId()) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }

        if (creditCartService.deleteCreditCard(id))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
