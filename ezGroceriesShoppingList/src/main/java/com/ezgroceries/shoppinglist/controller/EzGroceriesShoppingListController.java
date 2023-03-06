package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.client.CocktailDBClient;
import com.ezgroceries.shoppinglist.model.CocktailDBResponse;
import com.ezgroceries.shoppinglist.model.CocktailInput;
import com.ezgroceries.shoppinglist.model.CocktailOutput;
import com.ezgroceries.shoppinglist.model.ShoppingListInput;
import com.ezgroceries.shoppinglist.model.ShoppingListOutput;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author Aleksandar Todorov (jf08663)
 */
@RestController
public class EzGroceriesShoppingListController {

    private static final Logger log = LoggerFactory.getLogger(EzGroceriesShoppingListController.class);

    private final CocktailDBClient cocktailDBClient;

    public EzGroceriesShoppingListController(CocktailDBClient cocktailDBClient) {
        this.cocktailDBClient = cocktailDBClient;
    }

    @GetMapping("/cocktails")
    public ResponseEntity<List<CocktailOutput>> getCocktails(@RequestParam String search) {
        log.info("Getting list of all cocktails containing : {}", search);
        CocktailDBResponse cocktailDBResponse = cocktailDBClient.searchCocktails(search);
        return ResponseEntity.ok(mapToCocktailOutput(cocktailDBResponse));
    }

    @PostMapping("/shopping-lists")
    public ResponseEntity<?> postShoppingLists(@RequestBody ShoppingListInput shoppingListInput) {
        log.info("Start creation of shopping list with name : {}", shoppingListInput.getName());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(UUID.randomUUID())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping("/shopping-lists/{shoppingListId}/cocktails")
    public ResponseEntity<?> postCocktailToShoppingList(@PathVariable String shoppingListId, @RequestBody CocktailInput cocktailInput) {
        log.info("Adding cocktail with id : {} to shoppingList with id : {} ", cocktailInput.getCocktailId(), shoppingListId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cocktailInput.getCocktailId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/shopping-lists/{shoppingListId}")
    public ResponseEntity<ShoppingListOutput> getShoppingList(@PathVariable String shoppingListId) {
        log.info("Get shopping list with id : {}", shoppingListId);
        return ResponseEntity.ok(getMockShoppingList());
    }

    @GetMapping("/shopping-lists")
    public ResponseEntity<List<ShoppingListOutput>> getAllShoppingLists() {
        log.info("Get all shopping lists");
        return ResponseEntity.ok(getMockAllShoppingLists());
    }

    static List<CocktailOutput> getMockCocktailsList() {
        return List.of(
                new CocktailOutput("23b3d85a-3928-41c0-a533-6538a71e17c4",
                        "Margerita",
                        "Cocktail glass",
                        "Rub the rim of the glass with the lime slice to make the salt stick to it. Take care to moisten..",
                        "https://www.thecocktaildb.com/images/media/drink/wpxpvu1439905379.jpg",
                        List.of("Tequila", "Triple sec", "Lime juice", "Salt")),
                new CocktailOutput("d615ec78-fe93-467b-8d26-5d26d8eab073",
                        "Blue Margerita",
                        "Cocktail glass",
                        "Rub rim of cocktail glass with lime juice. Dip rim in coarse salt..",
                        "https://www.thecocktaildb.com/images/media/drink/qtvvyq1439905913.jpg",
                        List.of("Tequila", "Triple sec", "Lime juice", "Salt")));
    }

    static ShoppingListOutput getMockShoppingList() {
        return new ShoppingListOutput(
                "90689338-499a-4c49-af90-f1e73068ad4f",
                "Stephanie's birthday",
                List.of("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao"));
    }

    static List<ShoppingListOutput> getMockAllShoppingLists() {
        return List.of(
                getMockShoppingList(),
                new ShoppingListOutput(
                        "6c7d09c2-8a25-4d54-a979-25ae779d2465",
                        "My Birthday",
                        List.of("Tequila", "Triple sec", "Lime juice", "Salt", "Blue Curacao")));
    }

    private List<CocktailOutput> mapToCocktailOutput(CocktailDBResponse cocktailDBResponse) {
        return cocktailDBResponse.getDrinks().stream()
                .map(drinkResource -> new CocktailOutput(
                        drinkResource.getIdDrink(),
                        drinkResource.getStrDrink(),
                        drinkResource.getStrGlass(),
                        drinkResource.getStrInstructions(),
                        drinkResource.getStrDrinkThumb(),
                        Stream.of(drinkResource.getStrIngredient1(), drinkResource.getStrIngredient2(), drinkResource.getStrIngredient3(),
                                        drinkResource.getStrIngredient4())
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }
}
