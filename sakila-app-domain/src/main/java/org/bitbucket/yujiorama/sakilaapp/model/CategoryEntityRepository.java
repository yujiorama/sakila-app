package org.bitbucket.yujiorama.sakilaapp.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryEntityRepository extends CrudRepository<CategoryEntity, Integer> {
    List<CategoryEntity> findAllByOrderByNameAsc();
}
