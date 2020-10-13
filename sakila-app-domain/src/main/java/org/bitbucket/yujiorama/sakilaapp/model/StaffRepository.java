package org.bitbucket.yujiorama.sakilaapp.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StaffRepository extends CrudRepository<Staff, Integer> {

    List<Staff> findAllByOrderByFirstNameAscLastNameAsc();
}
