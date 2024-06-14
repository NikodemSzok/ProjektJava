package pl.szok.zaliczenie.controller;

import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.szok.zaliczenie.dto.RecipeDto;
import pl.szok.zaliczenie.model.*;
import pl.szok.zaliczenie.service.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class RecipeController {

    private final RecipeService recipeService;
    private final ProductService productService;
    private final IngredientService ingredientService;
    private final UserProductService userProductService;


    @Autowired
    public RecipeController(RecipeService recipeService, ProductService productService, IngredientService ingredientService, UserProductService userProductService) {
        this.recipeService = recipeService;
        this.productService = productService;
        this.ingredientService = ingredientService;
        this.userProductService = userProductService;
    }

    @GetMapping("/addRecipe")
    public String showAddRecipeForm(Model model) {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setIngredients(new ArrayList<>(Collections.singletonList(new RecipeDto.IngredientDto())));
        model.addAttribute("recipe", recipeDto);
        return "addRecipe";
    }

    @PostMapping("/addRecipe")
    public String addRecipe(@ModelAttribute RecipeDto recipeDto, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }
        Recipe recipe = new Recipe();
        recipe.setName(recipeDto.getName());
        recipe.setInstructions(recipeDto.getInstructions());
        recipe = recipeService.saveRecipe(recipe);

        for (RecipeDto.IngredientDto ingredientDto : recipeDto.getIngredients()) {
            Product product = productService.findByName(ingredientDto.getName());
            if (product == null) {
                product = new Product();
                product.setName(ingredientDto.getName());
                System.out.println(ingredientDto.getName());
                product = productService.saveProduct(product);
            }
            Ingredient ingredient = new Ingredient();
            ingredient.setProduct(product); // Set the product
            ingredient.setQuantity(ingredientDto.getQuantity());
            ingredient.setRecipe(recipe);
            ingredientService.saveIngredient(ingredient);
        }

        return "redirect:/dashboard";
    }

    @GetMapping("/recipeDetails/{recipeId}")
    public String showRecipeDetails(@PathVariable Long recipeId, Model model,   HttpSession session) {
        UserProduct userProduct = new UserProduct();
        Ingredient ingredient = new Ingredient();
        model.addAttribute("userProduct", userProduct);
        model.addAttribute("ingredient", ingredient);
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }
        Recipe recipe = recipeService.findById(recipeId);
        if (recipe != null) {
            model.addAttribute("recipe", recipe);
            return "recipeDetails";
        } else {
            return "redirect:/dashboard";
        }
    }

    @PostMapping("/makeDish/{recipeId}")
    public String makeDish(@PathVariable Long recipeId, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }
        Recipe recipe = recipeService.findById(recipeId);
        for (Ingredient ingredient : recipe.getIngredients()) {
            // Znalezienie odpowiedniego Produktu Użytkownika
            UserProduct userProduct = userProductService.findByUserAndProduct(loggedUser, ingredient.getProduct());

            // Sprawdzenie czy użytkownik ma wystarczającą ilość, jeśli nie, to zwróćenie błędu
            if (userProduct.getQuantity() < ingredient.getQuantity()) {
                // Błąd
                return "redirect:/error";
            }

            // Odjęcie ilości składników od produktów użytkownika
            userProduct.setQuantity(userProduct.getQuantity() - ingredient.getQuantity());
            userProductService.saveUserProduct(userProduct);
        }

        // Przekierowanie na dashboard po poprawnym zadziałaniu
        return "redirect:/dashboard";
    }
}