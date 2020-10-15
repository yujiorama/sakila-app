package org.bitbucket.yujiorama.sakilaapp.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FilmActorRepository extends CrudRepository<FilmActor, FilmActorId> {
    List<FilmActor> findAllByOrderByActorIdAscFilmAsc();
}
