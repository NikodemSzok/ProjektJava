package pl.szok.zaliczenie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.szok.zaliczenie.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
