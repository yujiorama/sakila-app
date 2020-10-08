package org.bitbucket.yujiorama.sakilaapp.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FilmEntityRepository extends CrudRepository<FilmEntity, Integer> {
    List<FilmEntity> findAllByOrderByTitleAsc();
}
