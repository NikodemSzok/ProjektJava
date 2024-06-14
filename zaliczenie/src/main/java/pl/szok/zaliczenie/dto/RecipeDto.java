package pl.szok.zaliczenie.dto;

import jakarta.validation.constraints.NotBlank;
import pl.szok.zaliczenie.model.Product;

import java.util.List;

public class RecipeDto {
    @NotBlank
    private String name;
    @NotBlank
    private String instructions;
    private List<IngredientDto> ingredients;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public List<IngredientDto> getIngredients() {
        return ingredients;
    }

    public void setIngredients (List<IngredientDto> ingredients) {
        this.ingredients = ingredients;
    }

    public static class IngredientDto {
        @NotBlank
        private String name;
        private int quantity;

        private Product product;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }
    }
}