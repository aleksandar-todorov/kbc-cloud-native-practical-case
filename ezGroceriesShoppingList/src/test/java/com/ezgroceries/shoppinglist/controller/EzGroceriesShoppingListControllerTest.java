package com.ezgroceries.shoppinglist.controller;

import static com.ezgroceries.shoppinglist.controller.EzGroceriesShoppingListController.getMockAllShoppingLists;
import static com.ezgroceries.shoppinglist.controller.EzGroceriesShoppingListController.getMockCocktailsList;
import static com.ezgroceries.shoppinglist.controller.EzGroceriesShoppingListController.getMockShoppingList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ezgroceries.shoppinglist.model.CocktailInput;
import com.ezgroceries.shoppinglist.model.ShoppingListInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Aleksandar Todorov (jf08663)
 */
@WebMvcTest
class EzGroceriesShoppingListControllerTest {

    private static final String EMPTY_STRING = "";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getCocktails() throws Exception {
        mockMvc.perform(get("/cocktails?search=Russian"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(objectMapper.writeValueAsString(getMockCocktailsList())));
    }

    @Test
    void postShoppingLists() throws Exception {
        mockMvc.perform(post("/shopping-lists")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new ShoppingListInput())))
                .andExpect(status().isCreated())
                .andExpect(content().string(EMPTY_STRING));
    }

    @Test
    void postCocktailToShoppingList() throws Exception {
        mockMvc.perform(post("/shopping-lists/123/cocktails")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(new CocktailInput())))
                .andExpect(status().isCreated())
                .andExpect(content().string(EMPTY_STRING));
    }

    @Test
    void getShoppingList() throws Exception {
        mockMvc.perform(get("/shopping-lists/123"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(objectMapper.writeValueAsString(getMockShoppingList())));
    }

    @Test
    void getAllShoppingLists() throws Exception {
        mockMvc.perform(get("/shopping-lists"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(objectMapper.writeValueAsString(getMockAllShoppingLists())));
    }
}