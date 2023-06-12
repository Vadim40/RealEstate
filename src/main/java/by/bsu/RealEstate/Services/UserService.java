package by.bsu.RealEstate.Services;

import by.bsu.RealEstate.Models.User;
import by.bsu.RealEstate.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService  {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;





    public Page<User> findUsersWithPagination(int offset, int pageSize) {
        return userRepository.findAll(PageRequest.of(offset, pageSize));
    }

    public User findUserById(long    id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
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


}
