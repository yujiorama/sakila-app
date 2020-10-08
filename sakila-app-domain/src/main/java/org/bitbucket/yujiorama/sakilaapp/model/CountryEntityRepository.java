package org.bitbucket.yujiorama.sakilaapp.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CountryEntityRepository extends CrudRepository<CountryEntity, Integer> {
    List<CountryEntity> findAllByOrderByCountryAsc();
}
