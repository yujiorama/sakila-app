package org.bitbucket.yujiorama.sakilaapp.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RentalEntityRepository extends CrudRepository<RentalEntity, Integer> {
    List<RentalEntity> findAllByOrderByIdAsc();
}