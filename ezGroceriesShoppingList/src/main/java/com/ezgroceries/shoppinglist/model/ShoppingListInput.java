package com.ezgroceries.shoppinglist.model;

/**
 * @author Aleksandar Todorov (jf08663)
 */
public class ShoppingListInput {

    private String name;

    public ShoppingListInput() {
    }

    public ShoppingListInput(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
