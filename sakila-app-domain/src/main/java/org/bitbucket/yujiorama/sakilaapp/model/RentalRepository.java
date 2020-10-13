package org.bitbucket.yujiorama.sakilaapp.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RentalRepository extends CrudRepository<Rental, Integer> {
    List<Rental> findAllByOrderByIdAsc();
}
