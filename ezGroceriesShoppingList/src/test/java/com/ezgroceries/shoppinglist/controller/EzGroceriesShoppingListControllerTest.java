package com.ezgroceries.shoppinglist.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ezgroceries.shoppinglist.client.CocktailDBClient;
import com.ezgroceries.shoppinglist.entity.ShoppingListEntity;
import com.ezgroceries.shoppinglist.model.CocktailDBResponse;
import com.ezgroceries.shoppinglist.model.CocktailDBResponse.DrinkResource;
import com.ezgroceries.shoppinglist.model.CocktailInput;
import com.ezgroceries.shoppinglist.model.CocktailOutput;
import com.ezgroceries.shoppinglist.model.ShoppingListInput;
import com.ezgroceries.shoppinglist.model.ShoppingListOutput;
import com.ezgroceries.shoppinglist.service.CocktailService;
import com.ezgroceries.shoppinglist.service.ShoppingListService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Aleksandar Todorov (jf08663)
 */
@WebMvcTest
class EzGroceriesShoppingListControllerTest {

    private static final String EMPTY_STRING = "";
    private static final UUID RANDOM_UUID = UUID.fromString("9cc5db6f-2e35-4e87-8192-e62e8a839a33");
    private static final String NAME = "Name";
    public static final String LOCATION = "location";

    @MockBean
    private CocktailDBClient cocktailDBClient;

    @MockBean
    private CocktailService cocktailService;

    @MockBean
    private ShoppingListService shoppingListService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getCocktails() throws Exception {
        when(cocktailDBClient.searchCocktails(anyString())).thenReturn(getMockCocktailDBResponse());
        when(cocktailService.mergeCocktails(any())).thenReturn(getMockCocktailsList());

        mockMvc.perform(get("/cocktails?search=Russian"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(objectMapper.writeValueAsString(getMockCocktailsList())));
    }

    @Test
    void postShoppingLists() throws Exception {
        when(shoppingListService.create(anyString())).thenReturn(getShoppingListEntity());

        mockMvc.perform(post("/shopping-lists")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(new ShoppingListInput(NAME))))
                .andExpect(status().isCreated())
                .andExpect(header().string(LOCATION, "http://localhost/shopping-lists/" + RANDOM_UUID))
                .andExpect(content().string(EMPTY_STRING));
    }

    @Test
    void postCocktailToShoppingList() throws Exception {
        doNothing().when(shoppingListService).addCocktail(anyString(), anyString());

        mockMvc.perform(post("/shopping-lists/123/cocktails")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(getCocktailInput())))
                .andExpect(status().isCreated())
                .andExpect(header().string(LOCATION, "http://localhost/shopping-lists/123/cocktails/" + RANDOM_UUID))
                .andExpect(content().string(EMPTY_STRING));
    }

    @Test
    void getShoppingList() throws Exception {
        when(shoppingListService.getById(anyString())).thenReturn(getMockShoppingList());

        mockMvc.perform(get("/shopping-lists/123"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(objectMapper.writeValueAsString(getMockShoppingList())));
    }

    @Test
    void getAllShoppingLists() throws Exception {
        when(shoppingListService.getAll()).thenReturn(getMockAllShoppingLists());

        mockMvc.perform(get("/shopping-lists"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
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

    private List<CocktailOutput> getMockCocktailsList() {
        return List.of(
                new CocktailOutput("23b3d85a-3928-41c0-a533-6538a71e17c4",
                        "Margerita",
                        "Cocktail glass",
                        "Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..",
                        "https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg",
                        Set.of("Tequila", "Triple sec", "Lime juice", "Salt")),
                new CocktailOutput("d615ec78-fe93-467b-8d26-5d26d8eab073",
                        "Blue Margerita",
                        "Cocktail glass",
                        "Rub rim of cocktail glass with lime juice. Dip rim in coarse salt..",
                        "https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg",
                        Set.of("Tequila", "Triple sec", "Lime juice", "Salt")));
    }

    private ShoppingListOutput getMockShoppingList() {
        return new ShoppingListOutput(
                "90689338-499a-4c49-af90-f1e73068ad4f",
                "Stephanie's birthday",
                Set.of("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao"));
    }

    private List<ShoppingListOutput> getMockAllShoppingLists() {
        return List.of(
                getMockShoppingList(),
                new ShoppingListOutput(
                        "6c7d09c2-8a25-4d54-a979-25ae779d2465",
                        "My Birthday",
                        Set.of("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao")));
    }

    private ShoppingListEntity getShoppingListEntity() {
        ShoppingListEntity shoppingListEntity = new ShoppingListEntity();
        shoppingListEntity.setId(RANDOM_UUID);
        return shoppingListEntity;
    }

    private CocktailInput getCocktailInput() {
        CocktailInput cocktailInput = new CocktailInput();
        cocktailInput.setCocktailId(RANDOM_UUID.toString());
        return cocktailInput;
    }
}