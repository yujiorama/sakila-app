package org.bitbucket.yujiorama.sakilaapp.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressEntityRepository extends CrudRepository<AddressEntity, Long> {

    List<AddressEntity> findAllByOrderByIdAsc();
}
