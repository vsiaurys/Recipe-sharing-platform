package lt.techin.recipesharingplatform.services;

import lt.techin.recipesharingplatform.models.User;
import lt.techin.recipesharingplatform.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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

    public boolean existsUser(User user) {

        ExampleMatcher matchDisplayName = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withIgnorePaths("email")
                .withIgnorePaths("password")
                .withIgnorePaths("firstName")
                .withIgnorePaths("lastName")
                .withIgnorePaths("gender")
                .withIgnorePaths("role");
        Example<User> exampleForDisplayName = Example.of(user, matchDisplayName);

        ExampleMatcher matchEmail = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withIgnorePaths("displayName")
                .withIgnorePaths("password")
                .withIgnorePaths("firstName")
                .withIgnorePaths("lastName")
                .withIgnorePaths("gender")
                .withIgnorePaths("role");
        Example<User> exampleForEmail = Example.of(user, matchEmail);

        return userRepository.exists(exampleForDisplayName) || userRepository.exists(exampleForEmail);
    }
}