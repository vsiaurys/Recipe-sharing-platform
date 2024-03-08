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

    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    public boolean existsUserByDisplayName(String displayName) {
        return userRepository.existsByDisplayName(displayName);
    }

    public boolean existsUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
