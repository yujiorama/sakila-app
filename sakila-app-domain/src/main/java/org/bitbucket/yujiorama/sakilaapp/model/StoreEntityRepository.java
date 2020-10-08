package org.bitbucket.yujiorama.sakilaapp.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StoreEntityRepository extends CrudRepository<StoreEntity, Integer> {
    List<StoreEntity> findAllByOrderByIdAsc();
}
