package org.bitbucket.yujiorama.sakilaapp.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LanguageRepository extends CrudRepository<Language, Integer> {
    List<Language> findAllByOrderByNameAsc();
}
