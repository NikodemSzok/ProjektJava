package pl.szok.zaliczenie.repository;

import org.springframework.data.repository.CrudRepository;
import pl.szok.zaliczenie.model.Product;
import pl.szok.zaliczenie.model.User;
import pl.szok.zaliczenie.model.UserProduct;
import java.util.List;
import java.util.Optional;

public interface UserProductRepository extends CrudRepository<UserProduct, Long> {
    List<UserProduct> findByUserId(Long userId);
    Optional<UserProduct> findByUserAndProduct(User user, Product product);
}
