package com.ezgroceries.shoppinglist.service;

import com.ezgroceries.shoppinglist.entity.CocktailEntity;
import com.ezgroceries.shoppinglist.entity.ShoppingListEntity;
import com.ezgroceries.shoppinglist.model.ShoppingListOutput;
import com.ezgroceries.shoppinglist.repository.CocktailRepository;
import com.ezgroceries.shoppinglist.repository.ShoppingListRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * @author Aleksandar Todorov (jf08663)
 */
@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final CocktailRepository cocktailRepository;

    public ShoppingListService(ShoppingListRepository shoppingListRepository, CocktailRepository cocktailRepository) {
        this.shoppingListRepository = shoppingListRepository;
        this.cocktailRepository = cocktailRepository;
    }

    public ShoppingListEntity create(String name) {
        return shoppingListRepository.save(new ShoppingListEntity(name));
    }

    public void addCocktail(String shoppingListId, String cocktailId) {
        Optional<ShoppingListEntity> shoppingListEntity = shoppingListRepository.findById(UUID.fromString(shoppingListId));
        if (shoppingListEntity.isEmpty()) {
            throw new IllegalArgumentException(String.format("shopping list with id : %s does not exists!", shoppingListId));
        }
        Optional<CocktailEntity> cocktailEntity = cocktailRepository.findById(UUID.fromString(cocktailId));
        if (cocktailEntity.isEmpty()) {
            throw new IllegalArgumentException(String.format("cocktail with id : %s does not exists!", cocktailId));
        }

        shoppingListEntity.get().addCocktail(cocktailEntity.get());
        shoppingListRepository.save(shoppingListEntity.get());
    }

    public ShoppingListOutput getById(String shoppingListId) {
        Optional<ShoppingListEntity> shoppingList = shoppingListRepository.findById(UUID.fromString(shoppingListId));
        if (shoppingList.isEmpty()) {
            throw new IllegalArgumentException(String.format("shopping list with id : %s does not exists!", shoppingListId));
        }
        return mapShoppingListEntityToOutput(shoppingList.get());
    }

    public List<ShoppingListOutput> getAll() {
        List<ShoppingListEntity> shoppingListEntities = shoppingListRepository.findAll();
        return shoppingListEntities.stream()
                .map(this::mapShoppingListEntityToOutput)
                .collect(Collectors.toList());
    }

    private ShoppingListOutput mapShoppingListEntityToOutput(ShoppingListEntity shoppingList) {
        return new ShoppingListOutput(
                shoppingList.getId().toString(),
                shoppingList.getName(),
                getUniqueIngredients(shoppingList));
    }

    private Set<String> getUniqueIngredients(ShoppingListEntity shoppingListEntity) {
        return shoppingListEntity.getCocktails()
                .stream()
                .map(CocktailEntity::getIngredients)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }
}
