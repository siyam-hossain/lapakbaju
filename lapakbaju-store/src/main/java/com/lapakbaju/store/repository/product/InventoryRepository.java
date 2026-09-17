package com.lapakbaju.store.repository.product;

import com.lapakbaju.store.entity.product.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

}
