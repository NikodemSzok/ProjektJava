package pl.szok.zaliczenie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.szok.zaliczenie.model.Ingredient;
import pl.szok.zaliczenie.model.Recipe;
import pl.szok.zaliczenie.model.User;
import pl.szok.zaliczenie.model.UserProduct;
import pl.szok.zaliczenie.repository.RecipeRepository;
import pl.szok.zaliczenie.repository.UserProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserProductService userProductService;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, UserProductService userProductService) {
        this.recipeRepository = recipeRepository;
        this.userProductService = userProductService;
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public List<Recipe> findAllRecipes() {
        return (List<Recipe>) recipeRepository.findAll();
    }

    public Recipe findById(Long recipeId) {
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        return recipe.orElse(null);
    }

    public List<Recipe> findAvailableRecipesForUser(User user) {
        List<UserProduct> userProducts = userProductService.findAllByUserId(user.getId());
        List<Recipe> availableRecipes = new ArrayList<>();

        for (Recipe recipe : findAllRecipes()) {
            boolean canMakeRecipe = true;

            for (Ingredient ingredient : recipe.getIngredients()) {
                UserProduct userProduct = userProducts.stream()
                        .filter(up -> up.getProduct().getId().equals(ingredient.getProduct().getId()))
                        .findFirst()
                        .orElse(null);

                if (userProduct == null || userProduct.getQuantity() < ingredient.getQuantity()) {
                    canMakeRecipe = false;
                    break;
                }
            }

            if (canMakeRecipe) {
                availableRecipes.add(recipe);
            }
        }

        return availableRecipes;
    }
}