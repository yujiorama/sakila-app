package org.bitbucket.yujiorama.sakilaapp.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FilmRepository extends CrudRepository<Film, Integer> {
    List<Film> findAllByOrderByTitleAsc();
}
