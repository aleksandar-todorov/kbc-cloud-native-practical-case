package com.ezgroceries.shoppinglist.model;

import java.util.Set;

/**
 * @author Aleksandar Todorov (jf08663)
 */
public class ShoppingListOutput {

    private String shoppingListId;
    private String name;
    private Set<String> ingredients;

    public ShoppingListOutput() {
    }

    public ShoppingListOutput(String shoppingListId, String name, Set<String> ingredients) {
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

    public Set<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<String> ingredients) {
        this.ingredients = ingredients;
    }
}
