package org.bitbucket.yujiorama.sakilaapp.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerEntityRepository extends CrudRepository<CustomerEntity, Integer> {
    List<CustomerEntity> findAllByOrderByFirstNameAscLastNameAsc();
}
