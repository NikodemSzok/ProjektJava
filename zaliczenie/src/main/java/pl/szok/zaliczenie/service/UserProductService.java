package pl.szok.zaliczenie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.szok.zaliczenie.model.Product;
import pl.szok.zaliczenie.model.User;
import pl.szok.zaliczenie.model.UserProduct;
import pl.szok.zaliczenie.repository.ProductRepository;
import pl.szok.zaliczenie.repository.UserProductRepository;
import pl.szok.zaliczenie.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserProductService {

    private final UserProductRepository userProductRepository;

    @Autowired
    public UserProductService(UserProductRepository userProductRepository) {
        this.userProductRepository = userProductRepository;
    }

    public UserProduct saveUserProduct(UserProduct userProduct) {
        return userProductRepository.save(userProduct);
    }

    public List<UserProduct> findAllByUserId(Long userId) {
        // Metoda aby znaleźć wszystkie produkty po ID użytkownika
        return userProductRepository.findByUserId(userId);
    }

    public UserProduct findByUserAndProduct(User user, Product product) {
        return userProductRepository.findByUserAndProduct(user, product).orElse(null);
    }
}