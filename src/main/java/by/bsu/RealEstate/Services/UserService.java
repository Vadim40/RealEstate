package by.bsu.RealEstate.Services;

import by.bsu.RealEstate.Models.RealEstate;
import by.bsu.RealEstate.Models.User;
import by.bsu.RealEstate.Repositories.RealEstateRepository;
import by.bsu.RealEstate.Repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    private RealEstateRepository realEstateRepository;

    public UserService(UserRepository userRepository, RealEstateRepository realEstateRepository) {
        this.userRepository = userRepository;
        this.realEstateRepository = realEstateRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Page<User> findAllUsersWithPagination(int offset, int pageSize) {
        return userRepository.findAll(PageRequest.of(offset, pageSize));
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException());
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean updateUser(long id, User user) {
        if (userRepository.findById(id).isPresent()) {
            User userUpdate = findUserById(id);
            userUpdate.setEmail(user.getEmail());
            userUpdate.setFirstName(user.getFirstName());
            userUpdate.setPassword(user.getPassword());
            userUpdate.setLogin(user.getLogin());
            userUpdate.setLastName(user.getLastName());
            userRepository.save(userUpdate);
            return true;
        }
        return false;

    }

    public boolean deleteUser(long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ArrayList<RealEstate> getRealEstateByUserId(long id) {
        return realEstateRepository.getRealEstateByUserId(id);
    }
}
