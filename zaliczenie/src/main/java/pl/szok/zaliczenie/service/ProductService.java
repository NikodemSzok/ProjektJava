package pl.szok.zaliczenie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.szok.zaliczenie.model.Product;
import pl.szok.zaliczenie.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    public Product findByName(String name) {
        return productRepository.findByName(name).orElse(null);
    }

    public Product findById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.orElse(null);
    }
}