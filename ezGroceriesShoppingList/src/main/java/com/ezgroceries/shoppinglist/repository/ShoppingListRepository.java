package com.ezgroceries.shoppinglist.repository;

import com.ezgroceries.shoppinglist.entity.ShoppingListEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Aleksandar Todorov (jf08663)
 */
@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingListEntity, UUID> {

}
