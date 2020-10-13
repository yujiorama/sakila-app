package org.bitbucket.yujiorama.sakilaapp.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActorRepository extends CrudRepository<Actor, Long> {
    List<Actor> findAllByOrderByFirstNameAscLastNameAsc();
}
