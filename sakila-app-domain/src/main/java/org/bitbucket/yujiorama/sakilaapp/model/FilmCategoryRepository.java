package org.bitbucket.yujiorama.sakilaapp.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FilmCategoryRepository extends CrudRepository<FilmCategory, FilmCategoryId> {
    List<FilmCategory> findAllByOrderByFilmIdAscCategoryAsc();
}
