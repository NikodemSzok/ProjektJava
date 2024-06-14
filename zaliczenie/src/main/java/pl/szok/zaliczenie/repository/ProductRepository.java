package pl.szok.zaliczenie.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import pl.szok.zaliczenie.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
    Optional<Product> findByName(String name);
}