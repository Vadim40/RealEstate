package by.bsu.RealEstate.Controllers;

import by.bsu.RealEstate.Mappers.RealEstateMapper;
import by.bsu.RealEstate.Models.DTO.RealEstateDTO;
import by.bsu.RealEstate.Models.RealEstate;
import by.bsu.RealEstate.Services.RealEstateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.function.Function;

@RestController
@RequestMapping("/estate")
public class RealEstateController {
    private final RealEstateService realEstateService;

    @Autowired
    public RealEstateController(RealEstateService realEstateService) {

        this.realEstateService = realEstateService;
    }


    @GetMapping("/all/{offset}/{pageSize}")
    public ResponseEntity<Page<RealEstateDTO>> getRealEstates(@PathVariable int offset, @PathVariable int pageSize) {
        if (!realEstateService.findRealEstatesWithPagination(offset, pageSize).isEmpty()) {
            RealEstateMapper realEstateMapper = new RealEstateMapper();
            Page<RealEstate> realEstates = realEstateService.findRealEstatesWithPagination(offset, pageSize);
            Page<RealEstateDTO> realEstateDTOS = realEstates.map(new Function<RealEstate, RealEstateDTO>() {
                @Override
                public RealEstateDTO apply(RealEstate realEstate) {
                    return realEstateMapper.mapRealEstateToRealEstateDTO(realEstate);
                }
            });
            return new ResponseEntity<>(realEstateDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all/{offset}/{pageSize}/{field}")
    public ResponseEntity<Page<RealEstateDTO>> getRealEstatesSort(@PathVariable int offset,
                                                                  @PathVariable int pageSize, @PathVariable String field) {
        if (!realEstateService.findRealEstatesWithPaginationAndSorting(offset, pageSize, field).isEmpty()) {
            RealEstateMapper realEstateMapper = new RealEstateMapper();
            Page<RealEstate> realEstates = realEstateService.findRealEstatesWithPaginationAndSorting(offset, pageSize, field);
            Page<RealEstateDTO> realEstateDTOS = realEstates.map(new Function<RealEstate, RealEstateDTO>() {
                @Override
                public RealEstateDTO apply(RealEstate realEstate) {
                    return realEstateMapper.mapRealEstateToRealEstateDTO(realEstate);
                }
            });
            return new ResponseEntity<>(realEstateDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all/{offset}/{pageSize}/price")
    public ResponseEntity<Page<RealEstateDTO>> getRealEstatesWithPrice(@PathVariable int offset, @PathVariable int pageSize,
                                                                       @RequestParam int leftPrice, @RequestParam int rightPrice) {
        if (!realEstateService.findRealEstatesWithPrice(leftPrice, rightPrice, offset, pageSize).isEmpty()) {
            RealEstateMapper realEstateMapper = new RealEstateMapper();
            Page<RealEstate> realEstates = realEstateService.findRealEstatesWithPrice(leftPrice, rightPrice, offset, pageSize);
            Page<RealEstateDTO> realEstateDTOS = realEstates.map(new Function<RealEstate, RealEstateDTO>() {
                @Override
                public RealEstateDTO apply(RealEstate realEstate) {
                    return realEstateMapper.mapRealEstateToRealEstateDTO(realEstate);
                }
            });
            return new ResponseEntity<>(realEstateDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/all/{offset}/{pageSize}/count_rooms")
    public ResponseEntity<Page<RealEstateDTO>> getRealEstatesWithCountRooms(@PathVariable int offset, @PathVariable int pageSize,
                                                                            @RequestParam int leftCountRooms, @RequestParam int rightCountRooms) {
        if (!realEstateService.findRealEstatesWithCountRooms(leftCountRooms, rightCountRooms, offset, pageSize).isEmpty()) {
            RealEstateMapper realEstateMapper = new RealEstateMapper();
            Page<RealEstate> realEstates = realEstateService.findRealEstatesWithCountRooms(leftCountRooms, rightCountRooms, offset, pageSize);
            Page<RealEstateDTO> realEstateDTOS = realEstates.map(new Function<RealEstate, RealEstateDTO>() {
                @Override
                public RealEstateDTO apply(RealEstate realEstate) {
                    return realEstateMapper.mapRealEstateToRealEstateDTO(realEstate);
                }
            });
            return new ResponseEntity<>(realEstateDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/allBy/{userId}/{offset}/{pageSize}")
    public ResponseEntity<Page<RealEstateDTO>> getRealEstatesByUserId(@PathVariable long userId,
                                                                      @PathVariable int offset,
                                                                      @PathVariable int pageSize) {
        if (!realEstateService.findRealEstatesByUserId(userId, offset, pageSize).isEmpty()) {
            RealEstateMapper realEstateMapper = new RealEstateMapper();
            Page<RealEstate> realEstates = realEstateService.findRealEstatesByUserId(userId, offset, pageSize);
            Page<RealEstateDTO> realEstateDTOS = realEstates.map(new Function<RealEstate, RealEstateDTO>() {
                @Override
                public RealEstateDTO apply(RealEstate realEstate) {
                    return realEstateMapper.mapRealEstateToRealEstateDTO(realEstate);
                }
            });
            return new ResponseEntity<>(realEstateDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RealEstateDTO> getRealEstate(@PathVariable("id") long id) {
        RealEstateMapper realEstateMapper = new RealEstateMapper();
        RealEstate realEstate = realEstateService.findRealEstateById(id);
        RealEstateDTO realEstateDTO = realEstateMapper.mapRealEstateToRealEstateDTO(realEstate);
        return new ResponseEntity<>(realEstateDTO, realEstate.getId() != 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/new")
    public ResponseEntity createRealEstate(@RequestBody @Valid RealEstateDTO realEstateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            RealEstateMapper realEstateMapper = new RealEstateMapper();
            realEstateService.saveRealEstate(realEstateMapper.mapRealEstateDTOToRealEstate(realEstateDTO));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PutMapping("/{id}/edit")
    public ResponseEntity editRealEstate(@PathVariable("id") long id, @RequestBody @Valid RealEstateDTO realEstateDTO,
                                         BindingResult bindingResult) {
        RealEstateMapper realEstateMapper = new RealEstateMapper();
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(realEstateService.updateRealEstate(id, realEstateMapper.mapRealEstateDTOToRealEstate(realEstateDTO))) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity deleteRealEstate(@PathVariable("id") long id) {
        if (realEstateService.deleteRealEstate(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
