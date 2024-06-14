package pl.szok.zaliczenie.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.szok.zaliczenie.model.Recipe;
import pl.szok.zaliczenie.model.User;
import pl.szok.zaliczenie.model.UserProduct;
import pl.szok.zaliczenie.service.RecipeService;
import pl.szok.zaliczenie.service.UserProductService;

import java.util.List;

@Controller
public class DashboardController {

    private final UserProductService userProductService;
    private final RecipeService recipeService;

    @Autowired
    public DashboardController(UserProductService userProductService, RecipeService recipeService) {
        this.userProductService = userProductService;
        this.recipeService = recipeService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpSession session) {
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser != null) {
            List<UserProduct> userProducts = userProductService.findAllByUserId(loggedUser.getId());
            List<Recipe> availableRecipes = recipeService.findAvailableRecipesForUser(loggedUser);

            model.addAttribute("userProducts", userProducts);
            model.addAttribute("availableRecipes", availableRecipes);
            model.addAttribute("user", loggedUser);
            return "dashboard";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/allRecipes")
    public String showAllRecipes(Model model) {
        List<Recipe> allRecipes = recipeService.findAllRecipes();
        model.addAttribute("recipes", allRecipes);
        return "allRecipes";
    }
}
