package org.bitbucket.yujiorama.sakilaapp.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CityEntityRepository extends CrudRepository<CityEntity, Integer> {
    List<CityEntity> findAllByOrderByCityAsc();
}
