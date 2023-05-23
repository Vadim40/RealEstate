package by.bsu.RealEstate.Controllers;

import by.bsu.RealEstate.Mappers.CreditCardMapper;
import by.bsu.RealEstate.Models.CreditCard;
import by.bsu.RealEstate.Models.DTO.CreditCardDTO;
import by.bsu.RealEstate.Services.CreditCartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/credit-card")
public class CreditCardController {
    public final CreditCartService creditCartService;

    public CreditCardController(CreditCartService creditCartService) {
        this.creditCartService = creditCartService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CreditCardDTO>> getAllCards() {
        if (creditCartService.findCreditCards().isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        List<CreditCardDTO> creditCardDTOS = new ArrayList<>();
        CreditCardMapper creditCardMapper = new CreditCardMapper();
        for (CreditCard creditCard : creditCartService.findCreditCards()) {
            creditCardDTOS.add(creditCardMapper.mapCreditCardToCreditCadDTO(creditCard));
        }
        return new ResponseEntity<>(creditCardDTOS, HttpStatus.OK);
    }
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<CreditCardDTO>> getCreditCardsByUserId(@PathVariable long userId) {
        if (!creditCartService.findCreditCardsByUserId(userId).isEmpty()) {
            List<CreditCardDTO> creditCardDTOS = new ArrayList<>();
            CreditCardMapper creditCardMapper = new CreditCardMapper();
            for (CreditCard creditCard : creditCartService.findCreditCardsByUserId(userId)) {
                creditCardDTOS.add(creditCardMapper.mapCreditCardToCreditCadDTO(creditCard));
            }
            return new ResponseEntity<>(creditCardDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CreditCardDTO> getCreditCardById(@PathVariable long id) {
        CreditCardMapper creditCardMapper = new CreditCardMapper();
        CreditCardDTO creditCardDTO = creditCardMapper.mapCreditCardToCreditCadDTO(
                creditCartService.findCreditCardById(id));
        return new ResponseEntity<>(creditCardDTO, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity createCreditCard(@RequestBody @Valid CreditCardDTO creditCardDTO,
                                           BindingResult bindingResult) {
        if (!bindingResult.hasErrors())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        CreditCardMapper creditCardMapper = new CreditCardMapper();
        CreditCard creditCard = creditCardMapper.mapCreditCardDtoToCreditCard(creditCardDTO);
        creditCartService.saveCreditCard(creditCard);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity editCreditCard(@PathVariable long id, @RequestBody @Valid CreditCardDTO creditCardDTO,
                                         BindingResult bindingResult) {
        if (!bindingResult.hasErrors())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        CreditCardMapper creditCardMapper = new CreditCardMapper();
        if (creditCartService.updateCreditCard(id, creditCardMapper.mapCreditCardDtoToCreditCard(creditCardDTO)))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity deleteCreditCard(@PathVariable long id){
        if (creditCartService.deleteCreditCard(id))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
