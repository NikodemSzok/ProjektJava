package pl.szok.zaliczenie.repository;

import org.springframework.data.repository.CrudRepository;
import pl.szok.zaliczenie.model.Ingredient;

import java.util.List;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
    List<Ingredient> findByRecipeId(Long recipeId);
}