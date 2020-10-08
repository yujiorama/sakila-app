package org.bitbucket.yujiorama.sakilaapp.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaymentEntityRepository extends CrudRepository<PaymentEntity, Integer> {
    List<PaymentEntity> findAllByOrderByIdAsc();
}
