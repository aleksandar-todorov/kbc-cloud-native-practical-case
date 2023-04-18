package com.ezgroceries.shoppinglist.client;

import com.ezgroceries.shoppinglist.entity.CocktailEntity;
import com.ezgroceries.shoppinglist.model.CocktailDBResponse;
import com.ezgroceries.shoppinglist.model.CocktailDBResponse.DrinkResource;
import com.ezgroceries.shoppinglist.repository.CocktailRepository;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * @author Aleksandar Todorov (jf08663)
 */
@Component
class CocktailDBClientFallback implements CocktailDBClient {

    private final CocktailRepository cocktailRepository;

    public CocktailDBClientFallback(CocktailRepository cocktailRepository) {
        this.cocktailRepository = cocktailRepository;
    }

    @Override
    public CocktailDBResponse searchCocktails(String search) {
        List<CocktailEntity> cocktailEntities = cocktailRepository.findByNameContainingIgnoreCase(search);

        CocktailDBResponse cocktailDBResponse = new CocktailDBResponse();
        cocktailDBResponse.setDrinks(cocktailEntities.stream().map(cocktailEntity -> {
            CocktailDBResponse.DrinkResource resource = new CocktailDBResponse.DrinkResource();
            resource.setIdDrink(cocktailEntity.getIdDrink());
            resource.setStrDrink(cocktailEntity.getName());
            resource.setStrGlass(cocktailEntity.getGlass());
            resource.setStrInstructions(cocktailEntity.getInstructions());
            resource.setStrDrinkThumb(cocktailEntity.getImageLink());
            setIngredients(resource, cocktailEntity.getIngredients());
            return resource;
        }).collect(Collectors.toList()));

        return cocktailDBResponse;
    }

    private void setIngredients(DrinkResource resource, Set<String> ingredients) {
        Iterator<String> iterator = ingredients.iterator();
        if (!iterator.hasNext()) {
            return;
        }
        resource.setStrIngredient1(iterator.next());

        if (!iterator.hasNext()) {
            return;
        }
        resource.setStrIngredient2(iterator.next());

        if (!iterator.hasNext()) {
            return;
        }
        resource.setStrIngredient3(iterator.next());

        if (!iterator.hasNext()) {
            return;
        }
        resource.setStrIngredient4(iterator.next());
    }
}
