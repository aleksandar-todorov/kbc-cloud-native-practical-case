package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.client.CocktailDBClient;
import com.ezgroceries.shoppinglist.entity.ShoppingListEntity;
import com.ezgroceries.shoppinglist.model.CocktailDBResponse;
import com.ezgroceries.shoppinglist.model.CocktailInput;
import com.ezgroceries.shoppinglist.model.CocktailOutput;
import com.ezgroceries.shoppinglist.model.ShoppingListInput;
import com.ezgroceries.shoppinglist.model.ShoppingListOutput;
import com.ezgroceries.shoppinglist.service.CocktailService;
import com.ezgroceries.shoppinglist.service.ShoppingListService;
import java.net.URI;
import java.util.List;
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
    private final CocktailService cocktailService;
    private final ShoppingListService shoppingListService;

    public EzGroceriesShoppingListController(CocktailDBClient cocktailDBClient, CocktailService cocktailService,
            ShoppingListService shoppingListService) {
        this.cocktailDBClient = cocktailDBClient;
        this.cocktailService = cocktailService;
        this.shoppingListService = shoppingListService;
    }

    @GetMapping("/cocktails")
    public ResponseEntity<List<CocktailOutput>> getCocktails(@RequestParam String search) {
        log.info("Getting list of all cocktails containing : {}", search);
        CocktailDBResponse cocktailDBResponse = cocktailDBClient.searchCocktails(search);
        return ResponseEntity.ok(cocktailService.mergeCocktails(cocktailDBResponse.getDrinks()));
    }

    @PostMapping("/shopping-lists")
    public ResponseEntity<?> postShoppingLists(@RequestBody ShoppingListInput shoppingListInput) {
        log.info("Start creation of shopping list with name : {}", shoppingListInput.getName());

        ShoppingListEntity shoppingListEntity = shoppingListService.create(shoppingListInput.getName());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{shoppingListId}")
                .buildAndExpand(shoppingListEntity.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping("/shopping-lists/{shoppingListId}/cocktails")
    public ResponseEntity<?> postCocktailToShoppingList(@PathVariable String shoppingListId, @RequestBody CocktailInput cocktailInput) {
        log.info("Adding cocktail with id : {} to shoppingList with id : {} ", cocktailInput.getCocktailId(), shoppingListId);

        shoppingListService.addCocktail(shoppingListId, cocktailInput.getCocktailId());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cocktailId}")
                .buildAndExpand(cocktailInput.getCocktailId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/shopping-lists/{shoppingListId}")
    public ResponseEntity<ShoppingListOutput> getShoppingList(@PathVariable String shoppingListId) {
        log.info("Get shopping list with id : {}", shoppingListId);
        return ResponseEntity.ok(shoppingListService.getById(shoppingListId));
    }

    @GetMapping("/shopping-lists")
    public ResponseEntity<List<ShoppingListOutput>> getAllShoppingLists() {
        log.info("Get all shopping lists");
        return ResponseEntity.ok(shoppingListService.getAll());
    }
}
