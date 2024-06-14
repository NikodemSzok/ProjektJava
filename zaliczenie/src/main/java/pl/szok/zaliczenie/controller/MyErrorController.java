package pl.szok.zaliczenie.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            // Inny wiadomość na podstawie sprawdzenia statusCode
            if(statusCode == 404) {
                model.addAttribute("errorMessage", "Strona nie znaleziona");
            } else if(statusCode == 500) {
                model.addAttribute("errorMessage", "Błąd wewnętrzny serwera");
            } else {
                model.addAttribute("errorMessage", "Nieoczekiwany błąd");
            }
        }
        return "error";
    }
}
