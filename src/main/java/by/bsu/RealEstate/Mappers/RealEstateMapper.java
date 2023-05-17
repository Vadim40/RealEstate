package by.bsu.RealEstate.Mappers;

import by.bsu.RealEstate.Models.DTO.RealEstateDTO;
import by.bsu.RealEstate.Models.RealEstate;

public class RealEstateMapper {

    public RealEstateMapper() {
    }

    public RealEstateDTO mapRealEstateToRealEstateDTO(RealEstate realEstate) {
        RealEstateDTO realEstateDTO = new RealEstateDTO();
        realEstateDTO.setCountRooms(realEstate.getCountRooms());
        realEstateDTO.setPrice(realEstate.getPrice());
        realEstateDTO.setSquare(realEstate.getSquare());
        realEstateDTO.setType(realEstate.getType());
        return realEstateDTO;
    }

    public RealEstate mapRealEstateDTOToRealEstate(RealEstateDTO realEstateDTO) {
        RealEstate realEstate = new RealEstate();
        realEstate.setType(realEstateDTO.getType());
        realEstate.setPrice(realEstateDTO.getPrice());
        realEstate.setCountRooms(realEstateDTO.getCountRooms());
        realEstate.setSquare(realEstateDTO.getSquare());
        return realEstate;
    }
}
