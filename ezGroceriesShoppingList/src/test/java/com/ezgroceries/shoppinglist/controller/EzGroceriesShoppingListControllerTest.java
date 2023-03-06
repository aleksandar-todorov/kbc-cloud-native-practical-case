package com.ezgroceries.shoppinglist.controller;

import static com.ezgroceries.shoppinglist.controller.EzGroceriesShoppingListController.getMockAllShoppingLists;
import static com.ezgroceries.shoppinglist.controller.EzGroceriesShoppingListController.getMockCocktailsList;
import static com.ezgroceries.shoppinglist.controller.EzGroceriesShoppingListController.getMockShoppingList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ezgroceries.shoppinglist.client.CocktailDBClient;
import com.ezgroceries.shoppinglist.model.CocktailDBResponse;
import com.ezgroceries.shoppinglist.model.CocktailDBResponse.DrinkResource;
import com.ezgroceries.shoppinglist.model.CocktailInput;
import com.ezgroceries.shoppinglist.model.ShoppingListInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Aleksandar Todorov (jf08663)
 */
@WebMvcTest
class EzGroceriesShoppingListControllerTest {

    private static final String EMPTY_STRING = "";

    @MockBean
    private CocktailDBClient cocktailDBClient;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getCocktails() throws Exception {

        when(cocktailDBClient.searchCocktails(anyString())).thenReturn(getMockCocktailDBResponse());

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

    private CocktailDBResponse getMockCocktailDBResponse() {
        CocktailDBResponse cocktailDBResponse = new CocktailDBResponse();
        cocktailDBResponse.setDrinks(List.of(
                new DrinkResource("23b3d85a-3928-41c0-a533-6538a71e17c4",
                        "Margerita",
                        "Cocktail glass",
                        "Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..",
                        "https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg",
                        "Tequila", "Triple sec", "Lime juice", "Salt"),
                new DrinkResource("d615ec78-fe93-467b-8d26-5d26d8eab073",
                        "Blue Margerita",
                        "Cocktail glass",
                        "Rub rim of cocktail glass with lime juice. Dip rim in coarse salt..",
                        "https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg",
                        "Tequila", "Triple sec", "Lime juice", "Salt")));
        return cocktailDBResponse;
    }
}