package pl.szok.zaliczenie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.szok.zaliczenie.model.Ingredient;
import pl.szok.zaliczenie.model.Recipe;
import pl.szok.zaliczenie.model.User;
import pl.szok.zaliczenie.model.UserProduct;
import pl.szok.zaliczenie.repository.RecipeRepository;
import pl.szok.zaliczenie.repository.UserProductRepository;
import pl.szok.zaliczenie.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}