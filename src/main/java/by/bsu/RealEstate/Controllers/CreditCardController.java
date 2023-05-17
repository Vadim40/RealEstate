package by.bsu.RealEstate.Controllers;

import by.bsu.RealEstate.Services.CreditCartService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credit-card")
public class CreditCardController {
    public final CreditCartService creditCartService;

    public CreditCardController(CreditCartService creditCartService) {
        this.creditCartService = creditCartService;
    }
//    @GetMapping("/all")
//    public List<CreditCardDTO> getAllCards(){
//        List<CreditCardDTO> creditCardDTOS=new ArrayList<>();
//        CreditCardMapper creditCardMapper=new CreditCardMapper();
//        for(CreditCard creditCard: creditCartService.)
//    }
}
