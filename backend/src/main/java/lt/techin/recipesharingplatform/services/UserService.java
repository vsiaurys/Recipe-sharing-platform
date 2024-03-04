package lt.techin.recipesharingplatform.services;

import lt.techin.recipesharingplatform.models.User;
import lt.techin.recipesharingplatform.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //    public User findUser(String email) {
    //        User user = userRepository.findUserByEmail(email);
    //
    //        return user;
    //    }

    public User saveUser(User user) {
        System.out.println("Service");
        // return this.userRepository.save(user);
        return user;
    }
}
