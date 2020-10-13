package org.bitbucket.yujiorama.sakilaapp.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InventoryRepository extends CrudRepository<Inventory, Integer> {
    List<Inventory> findAllByOrderByIdAsc();
}
