package by.bsu.RealEstate.Controllers;

import by.bsu.RealEstate.Mappers.RealEstateMapper;
import by.bsu.RealEstate.Models.DTO.RealEstateDTO;
import by.bsu.RealEstate.Models.RealEstate;
import by.bsu.RealEstate.Services.CustomUserDetailsService;
import by.bsu.RealEstate.Services.RealEstateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estate")
public class RealEstateController {
    private final RealEstateService realEstateService;
    private final CustomUserDetailsService customUserDetailsService;


    @Autowired
    public RealEstateController(RealEstateService realEstateService, CustomUserDetailsService customUserDetailsService) {

        this.realEstateService = realEstateService;
        this.customUserDetailsService = customUserDetailsService;
    }
    private ResponseEntity<Page<RealEstateDTO>> getPageResponseEntity(Page<RealEstate> realEstates) {
        if (realEstates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        RealEstateMapper realEstateMapper = new RealEstateMapper();
        Page<RealEstateDTO> realEstateDTOS = realEstates.map(realEstateMapper::mapRealEstateToRealEstateDTO);
        return new ResponseEntity<>(realEstateDTOS, HttpStatus.OK);
    }



    @GetMapping("/all/{offset}/{pageSize}")
    public ResponseEntity<Page<RealEstateDTO>> getRealEstates(@PathVariable int offset, @PathVariable int pageSize) {
        Page<RealEstate> realEstates = realEstateService.findRealEstatesWithPagination(offset, pageSize);
        return getPageResponseEntity(realEstates);

    }


    @GetMapping("/all/{offset}/{pageSize}/{field}")
    public ResponseEntity<Page<RealEstateDTO>> getRealEstatesSort(
            @PathVariable int offset,
            @PathVariable int pageSize,
            @PathVariable String field) {
        Page<RealEstate> realEstates = realEstateService.findRealEstatesWithPaginationAndSorting(offset, pageSize, field);
        return getPageResponseEntity(realEstates);

    }

    @GetMapping("/all/{offset}/{pageSize}/price")
    public ResponseEntity<Page<RealEstateDTO>> getRealEstatesWithPrice(
            @PathVariable int offset, @PathVariable int pageSize,
            @RequestParam int leftPrice, @RequestParam int rightPrice) {
        Page<RealEstate> realEstates = realEstateService.findRealEstatesWithPrice(leftPrice, rightPrice, offset, pageSize);
        return getPageResponseEntity(realEstates);

    }


    @GetMapping("/all/{offset}/{pageSize}/count_rooms")
    public ResponseEntity<Page<RealEstateDTO>> getRealEstatesWithCountRooms(
            @PathVariable int offset, @PathVariable int pageSize,
            @RequestParam int leftCountRooms, @RequestParam int rightCountRooms) {
        Page<RealEstate> realEstates = realEstateService.findRealEstatesWithCountRooms(
                leftCountRooms, rightCountRooms, offset, pageSize);
        return getPageResponseEntity(realEstates);

    }
    @GetMapping("/allBy/{userId}/{offset}/{pageSize}")
    public ResponseEntity<Page<RealEstateDTO>> getRealEstatesByUserId(@PathVariable long userId,
                                                                      @PathVariable int offset,
                                                                      @PathVariable int pageSize) {
        Page<RealEstate> realEstates=realEstateService.findRealEstatesByUserId(userId, offset, pageSize);
        return getPageResponseEntity(realEstates);

    }

    @GetMapping("/{id}")
    public ResponseEntity<RealEstateDTO> getRealEstate(@PathVariable("id") long id) {
        RealEstateMapper realEstateMapper = new RealEstateMapper();
        RealEstate realEstate = realEstateService.findRealEstateById(id);
        RealEstateDTO realEstateDTO = realEstateMapper.mapRealEstateToRealEstateDTO(realEstate);
        return new ResponseEntity<>(realEstateDTO, realEstate.getId() != 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/new")
    public ResponseEntity<?> createRealEstate(@RequestBody @Valid RealEstateDTO realEstateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RealEstateMapper realEstateMapper = new RealEstateMapper();
        RealEstate realEstate=realEstateMapper.mapRealEstateDTOToRealEstate(realEstateDTO);
        realEstate.setUserId(customUserDetailsService.getAuthenticatedUser().getId());
        realEstateService.saveRealEstate(realEstate);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping("/{id}/edit")
    public ResponseEntity<?> editRealEstate(@PathVariable("id") long id, @RequestBody @Valid RealEstateDTO realEstateDTO,
                                         BindingResult bindingResult) {
        if (!customUserDetailsService.getAuthenticatedUser().getRole().equals("ADMIN")) {
            if (realEstateService.findRealEstateById(id).getUserId() != customUserDetailsService.getAuthenticatedUser().getId()) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }


        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RealEstateMapper realEstateMapper = new RealEstateMapper();
        RealEstate realEstate=realEstateMapper.mapRealEstateDTOToRealEstate(realEstateDTO);
        realEstate.setUserId(customUserDetailsService.getAuthenticatedUser().getId());
        realEstateService.updateRealEstate(id, realEstate) ;
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteRealEstate(@PathVariable("id") long id) {
        if (!customUserDetailsService.getAuthenticatedUser().getRole().equals("ADMIN")) {
            if (realEstateService.findRealEstateById(id).getUserId() != customUserDetailsService.getAuthenticatedUser().getId()) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }

        realEstateService.deleteRealEstate(id);
            return new ResponseEntity<>(HttpStatus.OK);
    }

}
