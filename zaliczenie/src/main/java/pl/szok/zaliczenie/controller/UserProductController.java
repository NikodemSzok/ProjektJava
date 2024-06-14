package pl.szok.zaliczenie.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.szok.zaliczenie.model.Product;
import pl.szok.zaliczenie.model.User;
import pl.szok.zaliczenie.model.UserProduct;
import pl.szok.zaliczenie.service.ProductService;
import pl.szok.zaliczenie.service.UserProductService;
import pl.szok.zaliczenie.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/userproduct")
public class UserProductController {

    private final UserProductService userProductService;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public UserProductController(UserProductService userProductService, UserService userService, ProductService productService) {
        this.userProductService = userProductService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping
    public String listUserProducts(Model model, @RequestParam("userId") Long userId) {
        model.addAttribute("userProducts", userProductService.findAllByUserId(userId));
        model.addAttribute("products", productService.findAllProducts());
        return "user_products";
    }

    @PostMapping("/add")
    public String addUserProduct(@RequestParam("productName") String productName, @RequestParam("quantity") Integer quantity, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) {
            return "redirect:/login";
        }

        // Szukanie produktu, jeśli nie znaleziono to stworzenie takiego
        Product product = productService.findByName(productName);
        if (product == null) {
            product = new Product();
            product.setName(productName);
            productService.saveProduct(product);
        }

        // Sprawdzenie czy użytkownik ma produkt
        UserProduct existingUserProduct = userProductService.findByUserAndProduct(user, product);
        if (existingUserProduct != null) {
            // Zaktualizowanie ilości produktu jeśli istnieje
            existingUserProduct.setQuantity(existingUserProduct.getQuantity() + quantity);
            userProductService.saveUserProduct(existingUserProduct);
        } else {
            // Stworzenie i zapisanie nowego Produktu Użytkownika, jeśli jeszcze nie istnieje
            UserProduct newUserProduct = new UserProduct();
            newUserProduct.setUser(user);
            newUserProduct.setProduct(product);
            newUserProduct.setQuantity(quantity);
            userProductService.saveUserProduct(newUserProduct);
        }

        return "redirect:/dashboard";
    }
}