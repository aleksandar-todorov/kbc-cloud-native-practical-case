package com.ezgroceries.shoppinglist.repository;

import com.ezgroceries.shoppinglist.entity.CocktailEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Aleksandar Todorov (jf08663)
 */
@Repository
public interface CocktailRepository extends JpaRepository<CocktailEntity, UUID> {

    List<CocktailEntity> findByIdDrinkIn(List<String> ids);

    List<CocktailEntity> findByNameContainingIgnoreCase(String search);
}
