package org.bitbucket.yujiorama.sakilaapp.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LanguageEntityRepository extends CrudRepository<LanguageEntity, Integer> {
    List<LanguageEntity> findAllByOrderByNameAsc();
}
