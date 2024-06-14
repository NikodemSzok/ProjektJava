package pl.szok.zaliczenie.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.szok.zaliczenie.model.User;
import pl.szok.zaliczenie.repository.UserRepository;
import pl.szok.zaliczenie.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String performLogin(@ModelAttribute User loginUser, Model model, HttpSession session) {
        User registeredUser = userService.findByUsername(loginUser.getUsername());

        if (registeredUser != null && registeredUser.getPassword().equals(loginUser.getPassword())) {
            session.setAttribute("loggedUser", registeredUser); // Przechowywanie użytkownika w sesji
            return "redirect:/dashboard";
        } else {
            model.addAttribute("loginError", "Invalid username or password");
            return "login";
        }
    }
}