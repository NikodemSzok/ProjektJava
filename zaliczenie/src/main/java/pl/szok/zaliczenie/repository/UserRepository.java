package pl.szok.zaliczenie.repository;

import org.springframework.data.repository.CrudRepository;
import pl.szok.zaliczenie.model.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}