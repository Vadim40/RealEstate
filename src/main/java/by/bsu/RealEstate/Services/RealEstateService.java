package by.bsu.RealEstate.Services;

import by.bsu.RealEstate.Repositories.RealEstateRepository;
import by.bsu.RealEstate.Models.RealEstate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RealEstateService {
    private final RealEstateRepository realEstateRepository;

    @Autowired
    public RealEstateService(RealEstateRepository realEstateRepository) {
        this.realEstateRepository = realEstateRepository;
    }
//    @PostConstruct
//    public  void create(){
//        String[] type= new String[]{"house","flat"};
//        for (int i=0; i<100;i++){
//            RealEstate realEstate= new RealEstate();
//            realEstate.setPrice((int)(Math.random()*50000)+50000);
//            realEstate.setSquare((int)(Math.random()*50)+75);
//            realEstate.setCountRooms((int)(Math.random()*3)+3);
//            realEstate.setType((type[(int)(Math.random()*2)+0]));
//            realEstateRepository.save(realEstate);
//        }
//    }

    public Page<RealEstate> findRealEstatesWithPrice(int leftPrice, int rightPrice,
                                                     int offset, int pageSize) {
        return realEstateRepository.findRealEstatesByPrice(leftPrice, rightPrice, PageRequest.of(offset, pageSize));
    }

    public Page<RealEstate> findRealEstatesWithCountRooms(int leftCountRooms, int rightCountRooms,
                                                          int offset, int pageSize) {
        return realEstateRepository.findRealEstatesByCountRooms(leftCountRooms, rightCountRooms,
                PageRequest.of(offset, pageSize));
    }

    public Page<RealEstate> findRealEstatesWithPagination(int offset, int pageSize) {
        return realEstateRepository.findAll(PageRequest.of(offset, pageSize));
    }

    public Page<RealEstate> findRealEstatesWithPaginationAndSorting(int offset, int pageSize, String field) {
        return realEstateRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
    }
    public Page<RealEstate> findRealEstatesByUserId(long id, int offset, int pageSize) {
        return realEstateRepository.findRealEstatesByUserId(id, PageRequest.of(offset,pageSize));
    }
    public RealEstate findRealEstateById(long id) {
        return realEstateRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void saveRealEstate(RealEstate realEstate) {
        realEstateRepository.save(realEstate);
    }


    public boolean updateRealEstate(long id, RealEstate realEstate) {
        if (realEstateRepository.findById(id).isPresent()) {
            RealEstate realEstateUpdate = findRealEstateById(id);
            realEstateUpdate.setType(realEstate.getType());
            realEstateUpdate.setCountRooms(realEstate.getCountRooms());
            realEstateUpdate.setSquare(realEstate.getSquare());
            realEstateUpdate.setPrice(realEstate.getPrice());
            realEstateRepository.save(realEstateUpdate);
            return true;
        }
        return false;
    }

    public boolean deleteRealEstate(long id) {
        if (realEstateRepository.findById(id).isPresent()) {
            realEstateRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
