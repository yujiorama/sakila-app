package org.bitbucket.yujiorama.sakilaapp.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StoreRepository extends CrudRepository<Store, Integer> {
    List<Store> findAllByOrderByIdAsc();
}
