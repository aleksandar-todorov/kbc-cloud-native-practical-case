package com.ezgroceries.shoppinglist.model;

import java.util.List;

/**
 * @author Aleksandar Todorov (jf08663)
 */
public class ShoppingListOutput {

    private String shoppingListId;
    private String name;
    private List<String> ingredients;

    public ShoppingListOutput() {
    }

    public ShoppingListOutput(String shoppingListId, String name, List<String> ingredients) {
        this.shoppingListId = shoppingListId;
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(String shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
