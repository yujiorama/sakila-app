package org.bitbucket.yujiorama.sakilaapp.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InventoryEntityRepository extends CrudRepository<InventoryEntity, Integer> {
    List<InventoryEntity> findAllByOrderByIdAsc();
}
